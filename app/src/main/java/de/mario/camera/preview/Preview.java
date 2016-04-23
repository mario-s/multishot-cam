package de.mario.camera.preview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mario.camera.PhotoActivable;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private List<Dim> previewSizeList;
    private List<Dim> pictureSizeList;
    private Dim previewSize;
    private Dim pictureSize;
    private boolean surfaceConfiguring = false;

    public Preview(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        initHolder();
        initSupportedSizes();
    }

    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    private void initHolder() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initSupportedSizes() {
        Camera.Parameters cameraParams = camera.getParameters();
        previewSizeList = toDimension(cameraParams.getSupportedPreviewSizes());
        pictureSizeList = toDimension(cameraParams.getSupportedPictureSizes());
    }

    final void setPictureSizeList(List<Dim> pictureSizeList) {
        this.pictureSizeList = pictureSizeList;
    }

    final void setPreviewSizeList(List<Dim> previewSizeList) {
        this.previewSizeList = previewSizeList;
    }

    private List<Dim> toDimension(List<Camera.Size> sizes) {
        List<Dim> dimensions = new ArrayList<>(sizes.size());
        for (Camera.Size size : sizes) {
            dimensions.add(new Dim(size));
        }
        return dimensions;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.e(PhotoActivable.DEBUG_TAG, e.getMessage(), e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        doSurfaceChanged(w, h);
    }

    private void doSurfaceChanged(int width, int height) {
        camera.stopPreview();

        if (!surfaceConfiguring) {
            boolean portrait = isPortrait();

            previewSize = determinePreviewSize(portrait, width, height);
            pictureSize = determinePictureSize(previewSize);
            surfaceConfiguring = adjustSurfaceLayoutSize(previewSize, portrait, width, height);
        }

        configureCamera();
        surfaceConfiguring = false;

        camera.startPreview();
    }

    private int getDisplayOrientation() {
        int angle;
        Display display = getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_90:
                angle = 0;
                break;
            case Surface.ROTATION_180:
                angle = 270;
                break;
            case Surface.ROTATION_270:
                angle = 180;
                break;
            default:
                angle = 90;
                break;
        }
        return angle;
    }

    Display getDefaultDisplay() {
        return ((Activity) getContext()).getWindowManager().getDefaultDisplay();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private Dim determinePreviewSize(boolean portrait, int reqWidth, int reqHeight) {

        float reqRatio;
        if (portrait) {
            reqRatio = ((float) reqHeight) / reqWidth;
        } else {
            reqRatio = ((float) reqWidth) / reqHeight;
        }

        // Adjust surface size with the closest aspect-ratio
        return findSize(reqRatio, previewSizeList);
    }

    private Dim determinePictureSize(Dim previewSize) {
        for (Dim size : pictureSizeList) {
            if (size.equals(previewSize)) {
                return size;
            }
        }

        // if the preview size is not supported as a picture size
        float reqRatio = ((float) previewSize.getWidth()) / previewSize.getHeight();
        return findSize(reqRatio, pictureSizeList);
    }

    private Dim findSize(float reqRatio, List<Dim> sizeList) {
        Dim retSize = null;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        for (Dim size : sizeList) {
            curRatio = ((float) size.getWidth()) / size.getHeight();
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    private boolean adjustSurfaceLayoutSize(Dim previewSize, boolean portrait,
                                            int availableWidth, int availableHeight) {
        float tmpLayoutHeight, tmpLayoutWidth;
        if (portrait) {
            tmpLayoutHeight = previewSize.getWidth();
            tmpLayoutWidth = previewSize.getHeight();
        } else {
            tmpLayoutHeight = previewSize.getHeight();
            tmpLayoutWidth = previewSize.getWidth();
        }

        float fact = getFactor(availableWidth, availableHeight, tmpLayoutHeight, tmpLayoutWidth);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();

        int layoutHeight = (int) (tmpLayoutHeight * fact);
        int layoutWidth = (int) (tmpLayoutWidth * fact);

        boolean layoutChanged;
        if ((layoutWidth != this.getWidth()) || (layoutHeight != this.getHeight())) {
            layoutParams.height = layoutHeight;
            layoutParams.width = layoutWidth;
            setLayoutParams(layoutParams);
            layoutChanged = true;
        } else {
            layoutChanged = false;
        }

        return layoutChanged;
    }

    private float getFactor(int availableWidth, int availableHeight, float tmpLayoutHeight, float tmpLayoutWidth) {
        float factH, factW, fact;
        factH = availableHeight / tmpLayoutHeight;
        factW = availableWidth / tmpLayoutWidth;
        if (factH < factW) {
            fact = factH;
        } else {
            fact = factW;
        }
        return fact;
    }

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void configureCamera() {

        Camera.Parameters cameraParams = camera.getParameters();

        cameraParams.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        cameraParams.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());

        int angle = getDisplayOrientation();
        camera.setDisplayOrientation(angle);
        camera.setParameters(cameraParams);
    }

}

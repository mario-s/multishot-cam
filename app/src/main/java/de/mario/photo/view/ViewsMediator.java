package de.mario.photo.view;

import android.view.View;
import android.view.ViewGroup;

import de.mario.photo.R;
import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.SettingsAccessable;
import de.mario.photo.glue.ViewsMediatable;

/**
 * Mediator for the views.
 */

final class ViewsMediator implements ViewsMediatable{

    private final SettingsAccessable settingsAccess;

    private final CameraControlable cameraController;

    private ViewGroup preview;

    private final GridView gridView;

    private final LevelView levelView;

    ViewsMediator(SettingsAccessable settingsAccess, CameraControlable cameraController, GridView gridView, LevelView levelView) {
        this.settingsAccess = settingsAccess;
        this.cameraController = cameraController;
        this.gridView = gridView;
        this.levelView = levelView;
    }

    @Override
    public void setPreview(ViewGroup preview) {
        this.preview = preview;
    }

    @Override
    public void setupViews() {
        addView(cameraController.getPreview(), 0);
        addView(gridView, 1);
        addView(levelView, 2);
        addView(cameraController.getFocusView(), 3);
    }

    @Override
    public void removeViews(){
        preview.removeAllViews();
    }

    @Override
    public void updatePaintViews() {
        levelView.enable(isEnabled(R.string.level));
        gridView.setShowGrid(isEnabled(R.string.grid));
    }

    private void addView(View view, int index) {
        preview.addView(view, index);
    }

    private boolean isEnabled(int key) {
        return settingsAccess.isEnabled(key);
    }
}

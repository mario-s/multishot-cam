package de.mario.photo;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.mario.photo.exif.ExifWriter;
import de.mario.photo.exif.GeoTagFactory;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.support.MessageWrapper;
import roboguice.util.Ln;

/**
 * This class handles incoming messages from the sub parts.
 */
class MessageHandler extends Handler {

    private static final int PHOTOS_SAVED = R.string.photos_saved;
    private final PhotoActivity activity;

    MessageHandler(PhotoActivity activity) {
        super(Looper.getMainLooper());
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
       handleMessage(new MessageWrapper(message));
    }

    void handleMessage(MessageWrapper wrapper){
        if(wrapper.isDataEmpty()) {
            String msg = wrapper.getParcelAsString();
            switch (msg) {
                case PhotoActivable.PREPARE_FOR_NEXT:
                    activity.prepareForNextShot();
                    break;
                default:
                    toast(msg);
            }
        }else{
            handleMessageAsPictureInfo(wrapper);
        }
    }

    void toast(String msg) {
        Toast.makeText(activity.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void handleMessageAsPictureInfo(MessageWrapper wrapper){
        String[] pictures = wrapper.getStringArray(
                PhotoActivable.PICTURES);

        String folder = wrapper.getString(PhotoActivable.SAVE_FOLDER);
        updateExif(pictures);

        activity.processHdr(pictures);

        informAboutPictures(pictures, folder);

        activity.prepareForNextShot();

        Ln.d("ready for next photo session");
    }

    private void informAboutPictures(String[] pictures, String folder) {
        for(String pic : pictures) {
            activity.refreshPictureFolder(pic);
        }
    }

    private void updateExif(String [] pictures){
        Map<String, String> tags = new HashMap<>();

        addLocation(tags);

        updateTags(pictures, tags);
    }

    private void addLocation(Map<String, String> tags) {
        Location location = activity.getCurrentLocation();
        Ln.d("location: %s", location);
        if(location != null) {
            GeoTagFactory tagFactory = new GeoTagFactory();
            tags.putAll(tagFactory.create(location));
        }
    }

    private void updateTags(String[] pictures, Map<String, String> tags) {
        ExifWriter writer = new ExifWriter();
        for (String name : pictures) {
            File file = new File(name);
            writer.addTags(file, tags);
        }
    }
}

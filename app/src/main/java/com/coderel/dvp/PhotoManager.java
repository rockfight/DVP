package com.coderel.dvp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prasanna on 12/13/2014.
 */
class PhotoManager {

    public static int[] checkPhotoDimensions(String path) {

        int[] dimension = new int[3];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        dimension[0] = options.outWidth;
        dimension[1] = options.outHeight;
        dimension[2] = dimension[0] - dimension[1];
        return dimension;

    }

    public static int determineScaleFactor(int width, int height) {

        return (width / 600 < height / 600 ? width / 600 : height / 600);
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(Context context, boolean privacy) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String mediaFile = "IMG_" + timeStamp + ".jpg";
            String publicDirectory = "DVpics";
            File mediaStorageFile;
            File mediaStorageDirectory;

            if (privacy) {
                mediaStorageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), mediaFile);
            } else {
                mediaStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), publicDirectory);
                mediaStorageDirectory.mkdirs();
                mediaStorageFile = new File(mediaStorageDirectory, mediaFile);


            }
            return mediaStorageFile;

        } else {
            return null;
        }
    }

    public static boolean deleteFile(String path) {

        File file = new File(path);
        return file.delete();
    }
}


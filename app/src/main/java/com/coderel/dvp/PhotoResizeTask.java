package com.coderel.dvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Prasanna on 12/6/2014.
 */
public class PhotoResizeTask extends AsyncTask<Uri, Void, String> {



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Intent anotherPhotoIntent = new Intent(mContext,AnotherPhotoActivity.class);
//        Log.v("filePath in main activity", photoFile.getAbsolutePath());
        mContext.startActivity(anotherPhotoIntent);
//        Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.photoFile.getAbsolutePath());
//        Log.v("size of image view pic",Integer.toString(bitmap.getHeight()));
//        rootView.setImageBitmap(bitmap);





    }

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Bitmap squareBitmap;
    private final Context mContext;
    private ImageView rootView;
    ProgressDialog progressDialog;




    PhotoResizeTask(Activity context) {
        mContext = context;




    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       progressDialog = ProgressDialog.show(mContext, "wait", "Your photo is being made", true);
    }

    @Override
    protected String doInBackground(Uri... uris) {

        int photoShape; // to determine the shape of the picture (Portrait, landscape or square). Positive = landscape, Negative = portrait, 0 = square
        int scaleFactor;
        int cropValue;


        String photoPath = uris[0].getPath();
        Log.v("Photopath", photoPath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        int[] dimensions = PhotoManager.checkPhotoDimensions(photoPath);
        scaleFactor = PhotoManager.determineScaleFactor(dimensions[0], dimensions[1]);
        options.inSampleSize = scaleFactor;

        //load the bitmap in most minimum size possible
        Bitmap originalBitmap = BitmapFactory.decodeFile(photoPath, options);
//        Log.v("HEIGHT",Integer.toString(originalBitmap.getHeight()));
//        Log.v("WIDTH",Integer.toString(originalBitmap.getWidth()));

/*
        //delete the original photo after loading
        if (PhotoManager.deleteFile(photoPath)) {
            Log.v("FILE DELETED", "COMPLETELY");
        }
*/


        cropValue = Math.abs(originalBitmap.getWidth() - originalBitmap.getHeight());


        photoShape = (int) Math.signum(dimensions[2]);
        // photoShape = d.intValue();


        switch (photoShape) {
            case 1: {
                squareBitmap = resizePhoto(originalBitmap, cropValue / 2, 0);
                break;

            }
            case -1: {
                squareBitmap = resizePhoto(originalBitmap, 0, cropValue / 2);
                break;
            }
            case 0: {
                squareBitmap = originalBitmap;
                break;
            }
        }


        squareBitmap = Bitmap.createScaledBitmap(squareBitmap, 600, 600, false);

        //saving bitmap to JPEG in sd card
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        squareBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        MainActivity.photoFile = PhotoManager.getOutputMediaFile(mContext, false);


        try {
            Log.v(LOG_TAG, "CREATING FILE");
            MainActivity.photoFile.createNewFile();

            FileOutputStream fo = new FileOutputStream(MainActivity.photoFile);
            fo.write(bytes.toByteArray());
            fo.close();

            MediaScannerConnection.scanFile(mContext,
                    new String[]{MainActivity.photoFile.toString()}, null, null);


            Log.v(LOG_TAG, "FILE CREATED at "+MainActivity.photoFile.getAbsolutePath());
        } catch (IOException e) {
            Log.v(LOG_TAG, "CANNOT CREATE FILE");
            e.printStackTrace();
            return null;
        }

        return MainActivity.photoFile.getAbsolutePath();
    }

    private Bitmap resizePhoto(Bitmap originalBitmap, int cropX, int cropY) {


        Bitmap returnBitmap;

        returnBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth() - cropX, originalBitmap.getHeight() - cropY);
        returnBitmap = Bitmap.createBitmap(returnBitmap, cropX, cropY, returnBitmap.getWidth() - cropX - 1, returnBitmap.getHeight() - cropY - 1);
        Log.v(LOG_TAG, "width:" + returnBitmap.getWidth() + " Height:" + returnBitmap.getHeight());

        return returnBitmap;

    }
}


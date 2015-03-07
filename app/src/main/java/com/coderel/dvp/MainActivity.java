package com.coderel.dvp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.File;
/*
This is the main entry class
*/



public class MainActivity extends ActionBarActivity {


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final String PREFS_NAME = "instructionPreference";
    public static Uri fileUri;
    public static File photoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
        }





           setContentView(R.layout.activity_main);

            Button button = (Button) findViewById(R.id.button_take_picture);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startCameraIntent();
                }
            });


    }

    private  void startCameraIntent(){
        // create Intent to take a picture and return control to the calling application


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);


        PhotoResizeTask photoResizeTask = new PhotoResizeTask(MainActivity.this);
        photoResizeTask.execute(MainActivity.fileUri);




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("path", fileUri.getPath());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = Uri.fromFile(new File(savedInstanceState.getString("path")));

    }

    public void onCheckboxClick(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if(checked){
            SharedPreferences instructionPreference = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = instructionPreference.edit();
            editor.putBoolean(PREFS_NAME,true);
            editor.commit();
        } else {
            SharedPreferences instructionPreference = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = instructionPreference.edit();
            editor.putBoolean(PREFS_NAME,false);
            editor.commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(PhotoManager.getOutputMediaFile(getApplicationContext(), true));
    }



}

package com.coderel.dvp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class AnotherPhotoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_another_photo,null);
        ((ImageView)rootView.findViewById(R.id.imageView_picture)).setImageBitmap(BitmapFactory.decodeFile(MainActivity.photoFile.getAbsolutePath()));
        setContentView(rootView);

        Button takeAnotherPhoto = (Button) findViewById(R.id.button_take_another_photo);
        Button exitApplication = (Button) findViewById(R.id.button_exit_application);

        takeAnotherPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent nextPhotoIntent = new Intent(getApplicationContext(),MainActivity.class);
                nextPhotoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextPhotoIntent);

            }
        });

        exitApplication.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(getApplicationContext(),ShareActivity.class);
                startActivity(shareIntent);
            }
        });





//        Log.v("PHOTOPATH", filePath);
//        Bitmap imageBitmap = BitmapFactory.decodeFile(PhotoResizeTask.f.getAbsolutePath());
//        photoPreview.setImageBitmap(imageBitmap);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onTakeAnotherPicture(View view) {
        finish();
    }

    public void onExit(View view) {
        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_another_photo, menu);
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
}

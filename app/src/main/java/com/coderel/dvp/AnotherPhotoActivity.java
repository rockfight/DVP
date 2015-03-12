package com.coderel.dvp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class AnotherPhotoActivity extends ActionBarActivity {

    private InterstitialAd interstitial; //for interstitial Ad
    private AdRequest.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_another_photo,null);
        ((ImageView)rootView.findViewById(R.id.imageView_picture)).setImageBitmap(BitmapFactory.decodeFile(MainActivity.photoFile.getAbsolutePath()));
        setContentView(rootView);

        Button takeAnotherPhoto = (Button) findViewById(R.id.button_take_another_photo);
        Button exitApplication = (Button) findViewById(R.id.button_exit_application);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));


        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("B44E4973A97F1709E1E53CAC79CD5CE4").build();

        // Set an AdListener.
        interstitial.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Intent nextPhotoIntent = new Intent(getApplicationContext(),MainActivity.class);
                nextPhotoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextPhotoIntent);
            }
        });
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

        takeAnotherPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                displayInterstitial();
                /*Intent nextPhotoIntent = new Intent(getApplicationContext(),MainActivity.class);
                nextPhotoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextPhotoIntent);*/

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

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            Log.v("interstitial ad check", "LOADED");
            interstitial.show();

        }
    }
}

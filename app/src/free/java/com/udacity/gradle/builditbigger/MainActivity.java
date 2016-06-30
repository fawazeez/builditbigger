package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.JokeSource;
import com.udacity.gradle.builditbigger.api.EndpointsAsyncTask;


public class MainActivity extends AppCompatActivity {

    InterstitialAd interstitialAd;
    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
               requestNewInterstitialAd();
                getJoke();
            }
        });
        requestNewInterstitialAd();
    }

    private void requestNewInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("42030c7ed6864200").build();
        interstitialAd.loadAd(adRequest);
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

    public void tellJoke(View view){
        //JokeSource joke =  new JokeSource();
        if (interstitialAd.isLoaded())
            interstitialAd.show();
        else {
            getJoke();

        }
        //Toast.makeText(this, joke.getJoke(), Toast.LENGTH_SHORT).show();
    }

    private void getJoke() {
        String url = "http://10.0.2.2:8080/_ah/api/";
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, url));
    }



}

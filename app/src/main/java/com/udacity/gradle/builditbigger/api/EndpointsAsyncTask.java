package com.udacity.gradle.builditbigger.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.jokedisplay.JokeActivity;

import java.io.IOException;

/**
 * Created by fawaz on 6/28/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    static final String JOKETEXT = "com.udacity.gradle.JOKETEXT";
    private Context context;
    private EndpointTaskListener mEndpointTaskListener = null;
    private Exception merror;
    ProgressDialog progressDialog = null;

    public EndpointsAsyncTask(Activity activity)
    {
        if (activity!= null)
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {

if (progressDialog != null) {
    progressDialog.setMessage("Loading");
    progressDialog.show();
}
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        String url = params[0].second;
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(url)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

    context =params[0].first;

        try {
            return myApiService.sayHi("test").execute().getData();
        } catch (IOException e) {
            merror = e;
            return e.getMessage();
        }
    }

    public EndpointsAsyncTask setListener(EndpointTaskListener listener) {
        mEndpointTaskListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        if (progressDialog != null)
        progressDialog.dismiss();
        if (mEndpointTaskListener != null) {
            mEndpointTaskListener.onComplete(result, merror);
        }
        if (context != null) {
            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra(JOKETEXT, result);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCancelled() {
        if (this.mEndpointTaskListener != null) {
            merror = new InterruptedException("AsyncTask cancelled");
            this.mEndpointTaskListener.onComplete(null, merror);
        }

    }

    public static interface EndpointTaskListener {
        public void onComplete(String jokeString, Exception e);
    }
}
package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.api.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    Context mockContext = null;
    String mjokeString = null;
    Exception mError = null;
    CountDownLatch signal = null;


    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
        AsyncTaskFetchesJoke();
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
        super.tearDown();
    }


    public void AsyncTaskFetchesJoke() throws InterruptedException {
        String url = "http://10.0.2.2:8080/_ah/api/";
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(null);
        endpointsAsyncTask.setListener(new EndpointsAsyncTask.EndpointTaskListener() {
            @Override
            public void onComplete(String jokeString, Exception e) {
                mjokeString = jokeString;
                mError = e;
                signal.countDown();
            }
        }).execute(new Pair<Context,String>(mockContext,url));
        signal.await();

        assertFalse(TextUtils.isEmpty(mjokeString));
        assertNull(mError);

    }
}
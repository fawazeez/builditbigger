package com.udacity.gradle.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    static final String JOKETEXT = "com.udacity.gradle.JOKETEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        String joke = getIntent().getStringExtra(JOKETEXT);
        TextView jokeTextView = (TextView) findViewById(R.id.jokeTextView);
        if (joke != null)
        jokeTextView.setText(joke);
    }
}

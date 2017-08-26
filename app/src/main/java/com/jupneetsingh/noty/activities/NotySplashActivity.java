package com.jupneetsingh.noty.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jupneetsingh.noty.R;

/**
 * Created by jupneet on 24/08/17.
 */

public class NotySplashActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    private final int TIME_TO_STAY_ON_SPLASH_MILLIS = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openNoteListActivity();
            }
        }, TIME_TO_STAY_ON_SPLASH_MILLIS);

    }

    private void openNoteListActivity() {
        Intent intent = new Intent(this, NotesListActivity.class);
        startActivity(intent);
    }
}

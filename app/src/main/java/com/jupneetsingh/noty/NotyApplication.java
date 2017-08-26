package com.jupneetsingh.noty;

import android.app.Application;

import com.jupneetsingh.noty.database.NotesDatabaseHelper;

/**
 * Created by jupneet on 26/08/17.
 */

public class NotyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new NotesDatabaseHelper(this);

    }

}

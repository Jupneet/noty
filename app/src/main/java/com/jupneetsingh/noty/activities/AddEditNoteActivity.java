package com.jupneetsingh.noty.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jupneetsingh.noty.R;

/**
 * Created by jupneet on 26/08/17.
 */

public class AddEditNoteActivity extends AppCompatActivity {

    enum ACTION {ADD, EDIT}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);
    }


}

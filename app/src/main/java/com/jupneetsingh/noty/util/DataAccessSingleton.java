package com.jupneetsingh.noty.util;

import android.content.Context;

import com.jupneetsingh.noty.database.NotesDatabaseHelper;
import com.jupneetsingh.noty.models.NoteModel;

import java.util.ArrayList;

/**
 * Created by jupneet on 26/08/17.
 */

public class DataAccessSingleton {

    private DataAccessSingleton() {
    }

    private static volatile DataAccessSingleton sInstance;

    public static DataAccessSingleton getsInstance() {

        if (sInstance == null) {
            synchronized (DataAccessSingleton.class) {
                if (sInstance == null) {
                    sInstance = new DataAccessSingleton();
                }
            }
        }

        return sInstance;
    }

    public ArrayList<NoteModel> getAllNotes(Context context) {
        NotesDatabaseHelper dbHelper = new NotesDatabaseHelper(context);
        return dbHelper.getAllNotes();
    }

}

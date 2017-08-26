package com.jupneetsingh.noty.util;

import android.content.ContentValues;
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

    public static DataAccessSingleton getInstance() {

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

    public void insertNote(ContentValues values, Context context) {
        NotesDatabaseHelper dbHelper = new NotesDatabaseHelper(context);
        dbHelper.insertNote(values);
    }

    public void updateNote(ContentValues values, Context context, int noteIdToUpdate) {
        NotesDatabaseHelper dbHelper = new NotesDatabaseHelper(context);
        dbHelper.updateNote(noteIdToUpdate, values);
    }

}

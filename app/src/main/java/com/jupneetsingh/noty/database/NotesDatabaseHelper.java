package com.jupneetsingh.noty.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jupneetsingh.noty.constants.AppConstants;
import com.jupneetsingh.noty.models.NoteModel;

import java.util.ArrayList;

/**
 * Created by jupneet on 26/08/17.
 */

public class NotesDatabaseHelper extends SQLiteOpenHelper {


    public static final String NOTES_ID = "id";
    public static final String NOTE_TAGS = "note_tags";
    public static final String NOTE_TEXT = "note_text";
    public static final String NOTE_TITLE = "note_title";
    public static final String NOTES_TABLE = "notes_table";
    public static final String NOTE_CONTENT = "note_content";
    //comma separated


    String CREATE_NOTES_TABLE = "CREATE TABLE IF NOT EXISTS " +
            NOTES_TABLE + "(" + NOTE_TEXT + " TEXT," + NOTE_TITLE + " TEXT," +
            NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOTE_CONTENT + " TEXT," + NOTE_TAGS + " TEXT" + ")";


    public NotesDatabaseHelper(Context context) {
        super(context, AppConstants.NOTY_DATABASE_NAME, null, AppConstants.NOTY_DATABASE_VERSION);
    }

    public ArrayList<NoteModel> getAllNotes() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<NoteModel> notesToReturn = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + NOTES_TABLE + "", null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    NoteModel noteModel = new NoteModel();

                    String noteTitle = cursor.getString(cursor
                            .getColumnIndex(NOTE_TITLE));
                    String noteContent = cursor.getString(cursor
                            .getColumnIndex(NOTE_CONTENT));
                    String noteTags = cursor.getString(cursor
                            .getColumnIndex(NOTE_TAGS));
                    int noteId = cursor.getInt(cursor
                            .getColumnIndex(NOTES_ID));

                    noteModel.setNoteTags(noteTags);
                    noteModel.setNoteTitle(noteTitle);
                    noteModel.setNoteContent(noteContent);

                    notesToReturn.add(noteModel);

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            db.close();
        }
        return notesToReturn;
    }

    public void insertNote(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.insertWithOnConflict(NOTES_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateNote(int noteId, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.update(NOTES_TABLE, values, NOTES_ID + "='" + noteId + "'", null);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d("ERROR", "EORR");
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

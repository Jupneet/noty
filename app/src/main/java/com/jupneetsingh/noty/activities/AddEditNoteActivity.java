package com.jupneetsingh.noty.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jupneetsingh.noty.R;
import com.jupneetsingh.noty.constants.AppConstants;
import com.jupneetsingh.noty.database.NotesDatabaseHelper;
import com.jupneetsingh.noty.models.NoteModel;
import com.jupneetsingh.noty.util.DataAccessSingleton;

/**
 * Created by jupneet on 26/08/17.
 */

public class AddEditNoteActivity extends AppCompatActivity {

    enum ACTION {ADD, EDIT, VIEW}

    ACTION currentState;

    Button saveButton;
    Button editButton;
    EditText editTextTitle;
    EditText editTextContent;

    int noteModelId;
    String noteTitle;
    String noteContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);
        inflateUI();
    }

    private void inflateUI() {

        saveButton = (Button) findViewById(R.id.btnSave);
        editButton = (Button) findViewById(R.id.btnEdit);
        editTextTitle = (EditText) findViewById(R.id.eTxtTitle);
        editTextContent = (EditText) findViewById(R.id.eTxtContent);

        initValues();
    }

    private void initValues() {

        getActionForPage();
        updateUIAccordingToState();

        saveButton.setOnClickListener(saveButtonClickListener);
        editButton.setOnClickListener(editButtonClickListener);

    }

    private void updateUIAccordingToState()
    {
        switch (currentState) {
            case ADD:
                editButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
                break;
            case EDIT:

                editTextContent.setEnabled(true);
                editTextTitle.setEnabled(true);

                editButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
                break;
            case VIEW:

                if (noteTitle != null && noteContent != null) {
                    editTextTitle.setText(noteTitle);
                    editTextContent.setText(noteContent);
                }

                editTextContent.setEnabled(false);
                editTextTitle.setEnabled(false);

                editButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
                break;
        }

    }

    private void getActionForPage() {
        Intent intent = getIntent();

        if (intent.hasExtra(AppConstants.ADD_ACTION_CODE)) {
            currentState = ACTION.ADD;
        } else if (intent.hasExtra(AppConstants.EDIT_ACTION_CODE)) {
            currentState = ACTION.EDIT;
        } else if (intent.hasExtra(AppConstants.VIEW_ACTION_CODE)) {
            currentState = ACTION.VIEW;
            noteTitle = intent.getStringExtra(AppConstants.TITLE);
            noteContent = intent.getStringExtra(AppConstants.CONTENT);
        }
        if (intent.hasExtra(AppConstants.NOTE_ID)) {
            noteModelId = intent.getIntExtra(AppConstants.NOTE_ID, -1);
        }

    }

    private View.OnClickListener saveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (currentState) {
                case ADD:
                    saveNewNoteInDatabase();
                    break;
                case EDIT:
                    editNoteInDatabase(noteModelId);
                    break;
            }
            finish();
        }
    };

    private void editNoteInDatabase(int noteId) {

        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        ContentValues valuesToInsertInDatabase = new ContentValues();
        valuesToInsertInDatabase.put(NotesDatabaseHelper.NOTE_TITLE, title);
        valuesToInsertInDatabase.put(NotesDatabaseHelper.NOTE_CONTENT, content);

        //USE ASYNC TASK if this operation gets heavy
        DataAccessSingleton.getInstance().updateNote(valuesToInsertInDatabase
                , AddEditNoteActivity.this, noteId);

        showToast(getResources().getString(R.string.note_edited_message));

    }

    private void saveNewNoteInDatabase() {

        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        ContentValues valuesToInsertInDatabase = new ContentValues();
        valuesToInsertInDatabase.put(NotesDatabaseHelper.NOTE_TITLE, title);
        valuesToInsertInDatabase.put(NotesDatabaseHelper.NOTE_CONTENT, content);

        //USE ASYNC TASK if this operation gets heavy
        DataAccessSingleton.getInstance().insertNote(valuesToInsertInDatabase
                , AddEditNoteActivity.this);

        showToast(getResources().getString(R.string.note_added_message));

    }

    private View.OnClickListener editButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentState = ACTION.EDIT;
            updateUIAccordingToState();
        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

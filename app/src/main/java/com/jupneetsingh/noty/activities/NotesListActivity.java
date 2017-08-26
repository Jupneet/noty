package com.jupneetsingh.noty.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jupneetsingh.noty.R;
import com.jupneetsingh.noty.adapters.NoteListAdapter;
import com.jupneetsingh.noty.constants.AppConstants;
import com.jupneetsingh.noty.models.NoteModel;
import com.jupneetsingh.noty.util.DataAccessSingleton;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

    private final int NUMBER_OF_COLUMNS_GRID = 2;

    Toolbar toolbar;
    Button addNoteButton;
    RecyclerView listOfNotes;
    RelativeLayout noResultFoundEmptyState;

    NoteListAdapter listOfNotesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        inflateUI();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initValues();
    }

    private void inflateUI() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        addNoteButton = (Button) findViewById(R.id.btnAddNote);
        listOfNotes = (RecyclerView) findViewById(R.id.list_of_notes);
        noResultFoundEmptyState = (RelativeLayout) findViewById(R.id.noResultsFound);

        initValues();
    }

    private void initValues() {

        ArrayList<NoteModel> notes = getAllNotes();
        int sizeOfNotes = notes.size();

        if (sizeOfNotes > 0) {
            noResultFoundEmptyState.setVisibility(View.GONE);
            listOfNotesAdapter = new NoteListAdapter(this, notes);
            listOfNotes.setItemAnimator(new DefaultItemAnimator());
            listOfNotes.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS_GRID));
            listOfNotes.setAdapter(listOfNotesAdapter);
        } else {
            noResultFoundEmptyState.setVisibility(View.VISIBLE);
        }

        addNoteButton.setOnClickListener(addNoteClickedListener);
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void openAddEditActivity() {
        Intent addEditIntent = new Intent(this, AddEditNoteActivity.class);
        addEditIntent.putExtra(AppConstants.ADD_ACTION_CODE, true);
        startActivity(addEditIntent);
    }

    private ArrayList<NoteModel> getAllNotes() {
        return DataAccessSingleton.getInstance().getAllNotes(this);
    }

    private View.OnClickListener addNoteClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddEditActivity();
        }
    };

}

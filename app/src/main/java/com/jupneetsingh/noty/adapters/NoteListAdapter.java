package com.jupneetsingh.noty.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jupneetsingh.noty.R;
import com.jupneetsingh.noty.activities.AddEditNoteActivity;
import com.jupneetsingh.noty.constants.AppConstants;
import com.jupneetsingh.noty.models.NoteModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jupneet on 26/08/17.
 */

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<NoteModel> adapterData;

    public NoteListAdapter(Context context, ArrayList<NoteModel> noteModels) {

        this.context = context;
        adapterData = noteModels;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NoteViewHolder noteHolder = ((NoteViewHolder) holder);
        NoteModel noteModel = adapterData.get(position);

        noteHolder.noteTitle.setText(noteModel.getNoteTitle());
        noteHolder.noteContent.setText(noteModel.getNoteContent());

    }

    @Override
    public int getItemCount() {
        return adapterData.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteContent;
        RelativeLayout noteRow;

        public NoteViewHolder(View view) {
            super(view);

            noteTitle = (TextView) view.findViewById(R.id.txtTitle);
            noteRow = (RelativeLayout) view.findViewById(R.id.noteRow);
            noteContent = (TextView) view.findViewById(R.id.txtNoteContent);
            noteRow.setOnClickListener(noteRowClicked);
        }

        private View.OnClickListener noteRowClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteModel model = adapterData.get(getAdapterPosition());

                //WE CAN SEND THE WHOLE MODEL using parceable/serialisable
                Intent addEditIntent = new Intent(context, AddEditNoteActivity.class);
                addEditIntent.putExtra(AppConstants.VIEW_ACTION_CODE, true);
                addEditIntent.putExtra(AppConstants.NOTE_ID, model.getId());
                addEditIntent.putExtra(AppConstants.TITLE, model.getNoteTitle());
                addEditIntent.putExtra(AppConstants.CONTENT, model.getNoteContent());
                context.startActivity(addEditIntent);
            }
        };
    }
}

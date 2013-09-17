package com.mobileproto.lab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private DBHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView title = (TextView) findViewById(R.id.titleField);
        final TextView note = (TextView) findViewById(R.id.noteField);

        dbhandler = new DBHandler(this);
        dbhandler.open();

        List<Note> notesAll = dbhandler.getAllNotes();

        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, notesAll);

        final ListView notes = (ListView) findViewById(R.id.noteList);

        notes.setAdapter(aa);



        Button save = (Button)findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = title.getText().toString();
                String noteText = note.getText().toString();
                if (noteTitle != null && noteText != null){
                    Note curNote = dbhandler.createNote(noteTitle, noteText);
                    title.setText("");
                    note.setText("");
                    aa.insert(curNote,0);
                    aa.notifyDataSetChanged();
                    }
                }
        });

        save.setFocusable(false);

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note thisNote = dbhandler.getAllNotes().get(i);
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                String noteTitle = thisNote.getTitle();
                String noteText = thisNote.getText();
                in.putExtra("title", noteTitle);
                in.putExtra("text", noteText);
                startActivity(in);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

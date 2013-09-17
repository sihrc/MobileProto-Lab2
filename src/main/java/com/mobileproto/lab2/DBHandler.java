package com.mobileproto.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 9/17/13.
 */
public class DBHandler {

    private SQLiteDatabase database;
    private DatabaseModel model;

    private String[] allColumns = {DatabaseModel.COLUMN_ID, DatabaseModel.NOTE_TITLE, DatabaseModel.NOTE_TEXT};

    public DBHandler(Context context){
        model = new DatabaseModel(context);
    }

    public void open(){
        database = model.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Note createNote(String noteTitle, String noteText) {
        ContentValues values = new ContentValues();
        values.put(DatabaseModel.NOTE_TITLE, noteTitle);
        values.put(DatabaseModel.NOTE_TEXT, noteText);
        long insertId = database.insert(DatabaseModel.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(DatabaseModel.TABLE_NAME,
                allColumns, DatabaseModel.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DatabaseModel.TABLE_NAME, DatabaseModel.COLUMN_ID
                + " = " + id, null);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(DatabaseModel.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(0,note);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }
}

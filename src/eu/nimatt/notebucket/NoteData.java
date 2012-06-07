/**
 *  Copyright 2012 Mattias Nilsson
 * 
 *  This file is part of NoteBucket.
 *
 *  NoteBucket is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  NoteBucket is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with NoteBucket.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.nimatt.notebucket;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NoteData {
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allTagColumns = { DbHelper.COLUMN_ID,
			DbHelper.COLUMN_TAG };
	private String[] allNoteColumns = { DbHelper.COLUMN_ID,
			DbHelper.COLUMN_NOTE };

	public NoteData(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Tag createTag(String tag) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_TAG, tag);
		long insertId = database.insert(DbHelper.TABLE_TAGS, null,
				values);
		Cursor cursor = database.query(DbHelper.TABLE_TAGS,
				allTagColumns, DbHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Tag newTag = cursorToTag(cursor);
		cursor.close();
		return newTag;
	}
	
	public Note createNote(String note) {
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_NOTE, note);
		long insertId = database.insert(DbHelper.TABLE_NOTES, null,
				values);
		Cursor cursor = database.query(DbHelper.TABLE_NOTES,
				allNoteColumns, DbHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}


	public void deleteTag(Tag tag) {
		long id = tag.getId();
		System.out.println("Tag deleted with id: " + id);
		database.delete(DbHelper.TABLE_TAGS, DbHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteNote(Note note) {
		long id = note.getId();
		System.out.println("Note deleted with id: " + id);
		database.delete(DbHelper.TABLE_NOTES, DbHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Tag> getAllTags() {
		List<Tag> tags = new ArrayList<Tag>();

		Cursor cursor = database.query(DbHelper.TABLE_TAGS,
				allTagColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tag label = cursorToTag(cursor);
			tags.add(label);
			cursor.moveToNext();
		}
		
		cursor.close();
		return tags;
	}
	
	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();

		Cursor cursor = database.query(DbHelper.TABLE_NOTES,
				allNoteColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note label = cursorToNote(cursor);
			notes.add(label);
			cursor.moveToNext();
		}
		
		cursor.close();
		return notes;
	}

	private Tag cursorToTag(Cursor cursor) {
		Tag label = new Tag(cursor.getLong(0), cursor.getString(1));
		return label;
	}
	
	private Note cursorToNote(Cursor cursor) {
		Note label = new Note(cursor.getLong(0), cursor.getString(1));
		return label;
	}
}

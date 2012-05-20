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
	private String[] allColumns = { DbHelper.COLUMN_ID,
			DbHelper.COLUMN_TAG };

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
				allColumns, DbHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Tag newLabel = cursorToTag(cursor);
		cursor.close();
		return newLabel;
	}

	public void deleteTag(Tag tag) {
		long id = tag.getId();
		System.out.println("Tag deleted with id: " + id);
		database.delete(DbHelper.TABLE_TAGS, DbHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Tag> getAllTags() {
		List<Tag> comments = new ArrayList<Tag>();

		Cursor cursor = database.query(DbHelper.TABLE_TAGS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tag label = cursorToTag(cursor);
			comments.add(label);
			cursor.moveToNext();
		}
		
		cursor.close();
		return comments;
	}

	private Tag cursorToTag(Cursor cursor) {
		Tag label = new Tag(cursor.getLong(0), cursor.getString(1));
		return label;
	}
}

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

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CreateTag extends Activity {
	NoteData data = null;
	ListView noteList = null;
	List<Note> notes = null;
	List<Long> selected = new LinkedList<Long>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tag);
        try {
        	data = new NoteData(this);
    		data.open();
    		
    		noteList = (ListView) findViewById(R.id.noteList);

            noteList.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View view, int position,
    					long id) {
    				Note note = notes.get(position);
    				if (selected.contains(note.getId())) {
    					selected.remove(note.getId());
    				} else {
    					selected.add(note.getId());
    				}
    			}
    		});
		} catch(SQLException sqle) {
			Log.e(getString(R.string.create_tag_tag), sqle.getMessage());
			Toast.makeText(getApplicationContext(),
		               getString(R.string.db_error), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        
        notes = data.getAllNotes();
        noteList.setAdapter(new ArrayAdapter<Note>(this, 
        		R.layout.notelist_item, notes));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		try {
			data.close();
		}
		catch (Exception e) {
			Log.d(getString(R.string.create_note_tag), e.getMessage());
		}
	}
	
	public void createTag(View view) {
		String tagName = ((EditText) findViewById(R.id.create_tag_input)).getText().toString();
		
		try {
			Tag newTag = data.createTag(tagName);
			for(long note : selected) {
				data.createConnection(note, newTag.getId());
			}
		} catch(SQLException sqle) {
			Log.e(getString(R.string.create_tag_tag), sqle.getMessage());
			Toast.makeText(getApplicationContext(),
		               getString(R.string.db_error), Toast.LENGTH_LONG).show();
		} finally {
			try {
				data.close();
			} catch (Exception e) {
				Log.d(getString(R.string.create_tag_tag), e.getMessage());
			}
		}
		
		finish();
	}
	
	public void cancelTag(View view) {
		try {
			data.close();
		} catch (Exception e) {
			Log.d(getString(R.string.create_tag_tag), e.getMessage());
		}
		finish();
	}
}

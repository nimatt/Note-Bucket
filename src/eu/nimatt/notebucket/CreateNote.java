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

import android.app.Activity;
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

public class CreateNote extends Activity {
	NoteData data = null;
	ListView tagList = null;
	List<Tag> tags = null;
	List<Long> selected = new ArrayList<Long>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);
        
        try {
	        data = new NoteData(this);
			data.open();
			
			tagList = (ListView) findViewById(R.id.tagList);
			
			tagList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					Tag tag = tags.get(position);
					if (selected.contains(tag.getId())) {
						selected.remove(tag.getId());
					} else {
						selected.add(tag.getId());
					}
				}
			});
		} catch(SQLException sqle) {
			Log.e(getString(R.string.create_note_tag), sqle.getMessage());
			Toast.makeText(getApplicationContext(),
		               getString(R.string.db_error), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
        updateTagList();
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
	
	private void updateTagList() {
		tags = data.getAllTags();
        tagList.setAdapter(new ArrayAdapter<Tag>(this, 
        		R.layout.taglist_item, tags));
	}
	
	public void createNote(View view) {
		String noteName = ((EditText) findViewById(R.id.create_note_input)).getText().toString();
		
		try {
			Note newNote = data.createNote(noteName);
			for(long tag : selected) {
				data.createConnection(newNote.getId(), tag);
			}
		}
		catch(SQLException sqle) {
			Log.e(getString(R.string.create_note_tag), sqle.getMessage());
			Toast.makeText(getApplicationContext(),
		               getString(R.string.db_error), Toast.LENGTH_LONG).show();
		}
		finally {
			try {
				data.close();
			}
			catch (Exception e) {
				Log.d(getString(R.string.create_note_tag), e.getMessage());
			}
		}
		
		finish();
	}
	
	public void cancelNote(View view) {
		try {
			data.close();
		}
		catch (Exception e) {
			Log.d(getString(R.string.create_note_tag), e.getMessage());
		}
		
		finish();
	}
}
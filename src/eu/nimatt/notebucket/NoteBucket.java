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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Initial Activity for NoteBucket, shows list of all notes
 * 
 * @author Mattias Nilsson
 *
 */
public class NoteBucket extends Activity {
	NoteData data = null;
	ListView tagList = null;
	ListView noteList = null;
	List<Tag> tags = null;
	List<Note> notes = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        data = new NoteData(this);
        data.open();
        
        noteList = (ListView) findViewById(R.id.noteList);

        noteList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Note tag = notes.get(position);
				if(tag.getId() == -1) {
					Intent i = new Intent(getApplicationContext(), CreateNote.class);
					startActivity(i);
				}
				
			}
		});
        noteList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				boolean processed = false;
				Note note = notes.get(position);
				if (note.getId() != -1) {
					createDeleteNoteDialog(note);
					processed = true;
				}
				
				return processed;
			}
        });
        
        tagList = (ListView) findViewById(R.id.tagList);
        
        tagList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Tag tag = tags.get(position);
				if(tag.getId() == -1) {
					Intent i = new Intent(getApplicationContext(), CreateTag.class);
					startActivity(i);
				}
				
			}
		});
        tagList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				boolean processed = false;
				Tag tag = tags.get(position);
				if (tag.getId() != -1) {
					createDeleteTagDialog(tag);
					processed = true;
				}
				
				return processed;
			}
        });
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		tags = data.getAllTags();
        tags.add(new Tag(-1, getString(R.string.create_new)));
        tagList.setAdapter(new ArrayAdapter<Tag>(this, 
        		R.layout.list_item, tags));
        
        notes = data.getAllNotes();
        notes.add(new Note(-1, getString(R.string.create_new)));
        noteList.setAdapter(new ArrayAdapter<Note>(this, 
        		R.layout.list_item, notes));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		data.close();
	}
    
	private void createDeleteTagDialog(final Tag tag) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(String.format(getResources().getString(R.string.tag_delete_question),
				tag.getTag()))
		       .setCancelable(false)
		       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   data.deleteTag(tag);
		        	   tags.remove(tag);
		        	   tagList.setAdapter(new ArrayAdapter<Tag>(getApplicationContext(), 
		        			   R.layout.list_item, tags));
		           }
		       })
		       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		builder.create().show();
	}
	
	private void createDeleteNoteDialog(final Note note) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.note_delete_question))
		       .setCancelable(false)
		       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   data.deleteNote(note);
		        	   notes.remove(note);
		        	   noteList.setAdapter(new ArrayAdapter<Note>(getApplicationContext(), 
		        			   R.layout.list_item, notes));
		           }
		       })
		       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		builder.create().show();
	}
    
}
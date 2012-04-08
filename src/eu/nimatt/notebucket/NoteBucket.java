/**
 * Copyright 2012 Mattias Nilsson
 * 
 * This file is part of NoteBucket.
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

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Initial Activity for NoteBucket, shows list of all notes
 * 
 * @author Mattias Nilsson
 *
 */
public class NoteBucket extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView noteList = (ListView) findViewById(R.id.noteList);
        noteList.setAdapter(new ArrayAdapter<String>(this, 
        		R.layout.list_item, 
        		getResources().getStringArray(R.array.fake_notes)));
        ListView tagList = (ListView) findViewById(R.id.tagList);
        tagList.setAdapter(new ArrayAdapter<String>(this, 
        		R.layout.list_item, 
        		getResources().getStringArray(R.array.fake_notes)));
    }
}
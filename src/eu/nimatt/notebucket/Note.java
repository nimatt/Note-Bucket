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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.util.Log;


public class Note {
	private long id;
	private String note;
	private Set<Long> tagIds = new HashSet<Long>();

	public Note() {
		this(0, "");
	}
	
	public Note(long id, String note) {
		this.id = id;
		this.note = note;
	}
	
	public void addTag(long tagId) {
		tagIds.add(tagId);
	}
	
	public boolean hasTag(long tagId) {
		return tagIds.contains(tagId);
	}
	
	public List<Long> getTags() {
		Iterator<Long> iter = tagIds.iterator();
		List<Long> list = new ArrayList<Long>();
		
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		
		return list;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return note;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Note) {
			Note oNote = (Note) o;
			if (oNote.id == id) {
				equals = true;
			}
		}
		
		return equals;
	}
}
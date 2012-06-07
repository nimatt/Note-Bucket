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

public class Note {
	private long id;
	private String note;

	public Note() {
		this(0, "");
	}
	
	public Note(long id, String note) {
		this.id = id;
		this.note = note;
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
}
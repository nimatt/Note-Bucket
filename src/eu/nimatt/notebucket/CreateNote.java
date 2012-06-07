package eu.nimatt.notebucket;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNote extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);
	}
	
	public void createNote(View view) {
		String noteName = ((EditText) findViewById(R.id.create_note_input)).getText().toString();
		NoteData data = null;
		
		try {
			data = new NoteData(this);
			data.open();
			data.createNote(noteName);
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
		finish();
	}
}
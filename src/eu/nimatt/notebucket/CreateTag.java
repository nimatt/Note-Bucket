package eu.nimatt.notebucket;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTag extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tag);
	}
	
	public void createTag(View view) {
		String tagName = ((EditText) findViewById(R.id.create_tag_input)).getText().toString();
		NoteData data = null;
		
		try {
			data = new NoteData(this);
			data.open();
			data.createTag(tagName);
		}
		catch(SQLException sqle) {
			Log.e(getString(R.string.create_tag_tag), sqle.getMessage());
			Toast.makeText(getApplicationContext(),
		               getString(R.string.db_error), Toast.LENGTH_LONG).show();
		}
		finally {
			try {
				data.close();
			}
			catch (Exception e) {
				Log.d(getString(R.string.create_tag_tag), e.getMessage());
			}
		}
		
		finish();
	}
	
	public void cancelTag(View view) {
		finish();
	}
}

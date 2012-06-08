package eu.nimatt.notebucket;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

public class ListItemView extends TextView implements Checkable {
	private boolean checked;

	public ListItemView(Context context) {
		super(context);
	}
	
	public ListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		if(checked) {
			setBackgroundColor(getResources().getColor(R.color.selected));
		}
		else {
			setBackgroundColor(getResources().getColor(R.color.list_bg));
		}
	}

	public void toggle() {
		
	}
}

package org.agetac.tabs;

import java.util.Observable;
import java.util.Observer;

import org.agetac.R;
import org.agetac.model.Intervention;

import android.os.Bundle;

public class MessagesActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messages);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}
}

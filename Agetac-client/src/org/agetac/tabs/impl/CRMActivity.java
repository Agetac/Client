package org.agetac.tabs.impl;

import org.agetac.R;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.tabs.MyActivity;

import android.os.Bundle;

public class CRMActivity extends MyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crm);
	}

	@Override
	public ActionFlag getActionFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntity getTouchedEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}

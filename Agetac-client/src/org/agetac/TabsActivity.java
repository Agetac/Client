package org.agetac;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabsActivity extends TabActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		TabHost tHost = getTabHost();
		TabHost.TabSpec spec;
		Intent tabIntent;
		
		
	    // Initialize a TabSpec for each tab and add it to the TabHost
		tabIntent = new Intent().setClass(this, SITACActivity.class);
	    spec = tHost.newTabSpec("SITAC").setIndicator("SITAC",
	                      getResources().getDrawable(R.drawable.ic_tab_sitac))
	                  .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, SOEIActivity.class);
	    spec = tHost.newTabSpec("SOEI").setIndicator("SOEI",
                getResources().getDrawable(R.drawable.ic_tab_soei))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, MoyenActivity.class);
	    spec = tHost.newTabSpec("Moyens").setIndicator("Moyens",
                getResources().getDrawable(R.drawable.ic_tab_moyen))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, Tab4Activity.class);
	    spec = tHost.newTabSpec("TAB4").setIndicator("TAB4",
                getResources().getDrawable(R.drawable.ic_tab_tab4))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, Tab5Activity.class);
	    spec = tHost.newTabSpec("TAB5").setIndicator("TAB5",
                getResources().getDrawable(R.drawable.ic_tab_tab5))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tHost.setCurrentTab(0);
	}
}

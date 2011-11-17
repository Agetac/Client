package org.agetac;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabsActivity extends TabActivity {
	
	private static final String TAB_SITAC = "tab_sitac";
	private static final String TAB_SOEI = "tab_soei";
	private static final String TAB_MOYEN = "tab_moyen";
	private static final String TAB_TAB4 = "tab_tab4";
	private static final String TAB_TAB5 = "tab_tab5";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		TabHost tHost = getTabHost();
		TabHost.TabSpec spec;
		Intent tabIntent;
		
		
	    // Initialize a TabSpec for each tab and add it to the TabHost
		tabIntent = new Intent().setClass(this, SITACActivity.class);
	    spec = tHost.newTabSpec(TAB_SITAC).setIndicator(getString(R.string.sitac),
	                      getResources().getDrawable(R.drawable.ic_tab_sitac))
	                  .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, SOEIActivity.class);
	    spec = tHost.newTabSpec(TAB_SOEI).setIndicator(getString(R.string.soei),
                getResources().getDrawable(R.drawable.ic_tab_soei))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, MoyenActivity.class);
	    spec = tHost.newTabSpec(TAB_MOYEN).setIndicator(getString(R.string.moyen),
                getResources().getDrawable(R.drawable.ic_tab_moyen))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, Tab4Activity.class);
	    spec = tHost.newTabSpec(TAB_TAB4).setIndicator(getString(R.string.tab4),
                getResources().getDrawable(R.drawable.ic_tab_tab4))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tabIntent = new Intent().setClass(this, Tab5Activity.class);
	    spec = tHost.newTabSpec(TAB_TAB5).setIndicator(getString(R.string.tab5),
                getResources().getDrawable(R.drawable.ic_tab_tab5))
            .setContent(tabIntent);
	    tHost.addTab(spec);
	    
	    tHost.setCurrentTab(0);
	}
}

package org.agetac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private ProgressDialog connectionDialog;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button) findViewById(R.id.connectBtn)).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String title = getString(R.string.progress_title_connection);
				String message = getString(R.string.progress_connection);
				connectionDialog = ProgressDialog.show(MainActivity.this, title, message);
				connectionDialog.isIndeterminate();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO
						// demander une connexion au serveur

						// supprimer le progress dialog 
						connectionDialog.dismiss();
						
						// si la connexion est valide, lancer l'activitée
						startActivity(new Intent(MainActivity.this, TabsActivity.class));
					}
				}).start();
			}
		});
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		// fuite si on ne détruit pas manuellement le dialog
		// quand l'activitée est mise en pause
		if (connectionDialog != null) connectionDialog.dismiss();
	}
}
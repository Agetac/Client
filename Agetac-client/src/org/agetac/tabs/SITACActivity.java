package org.agetac.tabs;

import java.util.List;
import java.util.Observable;
import java.util.Random;

import org.agetac.R;
import org.agetac.command.AddEntityCommand;
import org.agetac.command.RemoveEntityCommand;
import org.agetac.model.Entity;
import org.agetac.model.Vehicule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SITACActivity extends AbstractActivity implements OnClickListener, OnItemClickListener {
	
	private Button addVehiculeBtn;
	private TextView nbCurrentVehiculeView;
	private int nbCurrentVehicule;
	private TextView nbFuturVehiculeView;
	private int nbFuturVehicule;
	private ListView vehiculeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		
		addVehiculeBtn = (Button) findViewById(R.id.addVehicule);
		addVehiculeBtn.setOnClickListener(this);
		
		nbCurrentVehiculeView = (TextView) findViewById(R.id.nbCurrentVehicule);
		nbFuturVehiculeView = (TextView) findViewById(R.id.nbFuturVehicule);
		
		vehiculeList = (ListView) findViewById(R.id.vehicules_listview);
		vehiculeList.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Random gen = new Random();
		
		switch (v.getId()) {
		
			case R.id.addVehicule:
				Entity e = new Vehicule("Vehicule "+(gen.nextInt(41)+1), false);
				controller.setLastEntity(e);
				controller.getCommands().get(AddEntityCommand.NAME).execute();
				break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adpt, View v, int index, long l) {
		try {
			final AdapterView adapter = adpt;
			final int position = index;
			AlertDialog.Builder confirmDelete = new AlertDialog.Builder(this);
			confirmDelete.setTitle(R.string.dialog_title_deletevehicule);
			confirmDelete.setMessage(R.string.dialog_deletevehicule);
			confirmDelete.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ArrayAdapter<Vehicule> vehicules = (ArrayAdapter) adapter.getAdapter();
					controller.setLastEntity(vehicules.getItem(position));
					controller.getCommands().get(RemoveEntityCommand.NAME).execute();
				}
			});
			confirmDelete.setNegativeButton(R.string.no, null);
			confirmDelete.show();
			
		} catch (ClassCastException e) {}
	}

	@Override
	public void update(Observable observable, Object data) {
		try {
			
			List<Vehicule> vehicules = (List<Vehicule>) data;
			ArrayAdapter<Vehicule> adapter = new ArrayAdapter<Vehicule>(
					this,
					android.R.layout.simple_list_item_1,
					vehicules
			);
			vehiculeList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			nbCurrentVehicule = 0;
			nbFuturVehicule = 0;
			
			for (int i=0; i<vehicules.size(); i++) {
				if (vehicules.get(i).isDeBase()) {
					nbCurrentVehicule++;
				} else {
					nbFuturVehicule++;
				}
			}
			nbCurrentVehiculeView.setText(String.valueOf(nbCurrentVehicule));
			nbFuturVehiculeView.setText(String.valueOf(nbFuturVehicule));
			
		} catch (ClassCastException e) {}
	}
}
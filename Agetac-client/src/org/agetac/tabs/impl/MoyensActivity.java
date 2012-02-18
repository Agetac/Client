package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.agetac.R;
import org.agetac.common.ActionFlag;
import org.agetac.entity.impl.Entity;
import org.agetac.entity.sign.IEntity;
import org.agetac.model.impl.Agent;
import org.agetac.model.impl.Groupe;
import org.agetac.model.impl.Position;
import org.agetac.model.impl.Vehicule;
import org.agetac.model.impl.Vehicule.EtatVehicule;
import org.agetac.pictogram.PictogramHolder;
import org.agetac.pictogram.sign.IPictogram;
import org.agetac.tabs.sign.AbstractActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MoyensActivity extends AbstractActivity implements OnClickListener, OnItemClickListener {
	
	private static final String TAG = "MoyensActivity";
	
	private ItemAdapter itemAdapter;
	private ListView listView;
	
	protected int idVeh = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.moyens);
	    
		listView = (ListView) findViewById(R.id.malistview);
	    listView.setOnItemClickListener(this);
	    itemAdapter = new ItemAdapter();
	    listView.setAdapter(itemAdapter);
	    
	    //Creation de la ArrayList qui nous permettra de remplir la listView
        List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On declare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Creation d'une HashMap pour ins�rer les informations du premier item de notre listView
        
        map = new HashMap<String, String>();        
        map.put("titre", "Type du vehicule");
        map.put("description", "Arrivee");
        //map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
        
        //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue moyens_list_item
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.moyens_list_item,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
        listView.setAdapter(mSchedule); 

		findViewById(R.id.bouton_demande_moyen).setOnClickListener(this);
		
     
	}
	
	@Override
	public void onItemClick(AdapterView<?> adpt, View v, int index, long l) {
////		try {/*
//			final AdapterView<?> adapter = adpt;
//		final int position = index;
//			AlertDialog.Builder confirmDelete = new AlertDialog.Builder(this);
//			confirmDelete.setTitle(R.string.dialog_title_deletevehicule);
//			confirmDelete.setMessage(R.string.dialog_deletevehicule);
//			confirmDelete.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					ItemAdapter itemAdpt = (ItemAdapter) adapter.getAdapter();
//					flag = ActionFlag.REMOVE;
//					touchedEntity = (IEntity) itemAdpt.getItem(position);
//					observable.setChanged();
//					observable.notifyObservers(MoyensActivity.this);
//				}
//			});
//			confirmDelete.setNegativeButton(R.string.no, null);
//			confirmDelete.show();*/
//			
//			
//			
////		} catch (ClassCastException e) {
////			android.util.Log.e(TAG, e.getMessage());
////		}*/
	}


	@Override
	public void onClick(View v) {
		try {
			flag = ActionFlag.ADD;
			Vehicule veh = new Vehicule("42"+idVeh, "FPT Janze", new Position(33.4, 48.8), "Caserne Beaulieu", EtatVehicule.PARTIS, new Groupe("UniqueID", null, new ArrayList<Vehicule>()));
	        List<IPictogram> pictos = PictogramHolder.getInstance(getBaseContext()).getPictograms();
	        touchedEntity = new Entity<Vehicule>(veh, pictos.get(0));
	        idVeh++;
			observable.setChanged();
			observable.notifyObservers(MoyensActivity.this);
	        
//			Builder box = new AlertDialog.Builder(this);
//			box.setTitle("Ajout de moyen");
//			box.setMessage("Voici la liste des moyens que vous pouvez ajoutez:");
//			CharSequence[] items = {"FPT", "VSAV", "CCGC", "Vehicule A", "Vehicule B", "Vehicule C", "Vehicule D"};
//			box.setSingleChoiceItems(items, 0, null);
//			box.show();
			}
			catch (ClassCastException e) {
			android.util.Log.e(TAG, e.getMessage());
			}
		
////		update();
//		List<IEntity> list_veh = new ArrayList<IEntity>();
//		list_veh.add(new VehiculeEntity(nouveau, null));
//		itemAdapter.setItems((List<IEntity>) list_veh);
//		listView.setAdapter(itemAdapter); 
	}
	
	@Override
	public void update() {
		List<IEntity> entities = controller.getInterventionEngine().getEntities();
		itemAdapter.setItems(entities);
	//	this.setContentView(itemAdapter.getView(position, convertView, parent))
		itemAdapter.notifyDataSetChanged();
	}
	
	public class ItemAdapter extends BaseAdapter {

		private List<IEntity> entities;

	    public void setItems(List<IEntity> entities) {
			this.entities = entities;
		}

		public int getCount() {
	        if (entities == null) {
	        	return 0;
	        }
	        return entities.size();
	    }

	    public Object getItem(int position) {
	        if (entities == null) {
	        	return null;
	        }
	        return entities.get(position);
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new View for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View itemView = getLayoutInflater().inflate(R.layout.moyens_list_item, null);
	        //View itemView = getLayoutInflater().inflate(R.layout.gridview_item, null);

	        ((TextView) itemView.findViewById(R.id.vehicule_name)).setText(entities.get(position).getModel().getName());
	        return itemView;
	    }
	}
	
	
}

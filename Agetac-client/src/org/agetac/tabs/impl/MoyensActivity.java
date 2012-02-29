package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.agetac.R;
import org.agetac.common.ActionFlag;
import org.agetac.entity.impl.Entity;
import org.agetac.entity.impl.Entity.EntityState;
import org.agetac.entity.sign.IEntity;
import org.agetac.model.impl.Groupe;
import org.agetac.model.impl.Position;
import org.agetac.model.impl.Vehicule;
import org.agetac.model.impl.Vehicule.EtatVehicule;
import org.agetac.pictogram.PictogramHolder;
import org.agetac.pictogram.sign.IPictogram;
import org.agetac.tabs.sign.AbstractActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MoyensActivity extends AbstractActivity implements OnClickListener, OnItemClickListener {
	
	private static final String TAG = "MoyensActivity";
	private static final String DATA_IMG = "data_img";
	private static final String DATA_TYPE = "data_type";
	private static final String DATA_CASERNE = "data_caserne";
	private static final String DATA_ETAT = "data_etat";
	private static final String DATA_GHARR = "data_gh_arrivee";
	private static final String DATA_GHRET = "data_gh_retour";
	
	private SimpleAdapter listAdapter;
	private ListView listView;
	private List<Hashtable<String, String>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moyens);
	    
		listView = (ListView) findViewById(R.id.moyens_listview);
	    listView.setOnItemClickListener(this);
	    LayoutInflater inflater = getLayoutInflater();
	    listView.addHeaderView(inflater.inflate(R.layout.moyens_list_header, null), null, false);	    
	    
	    // Creation de l'ArrayList qui nous permettra de remplir la listView
        data = new ArrayList<Hashtable<String, String>>();
        
        //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue moyens_list_item
        listAdapter = new SimpleAdapter(this.getBaseContext(), data, R.layout.moyens_list_item,
               new String[] {DATA_IMG, DATA_TYPE, DATA_CASERNE, DATA_ETAT, DATA_GHARR, DATA_GHRET}, new int[] {R.id.img, R.id.type_vehicule, R.id.caserne_vehicule, R.id.etat_vehicule, R.id.gh_arrivee, R.id.gh_retour});
        listView.setAdapter(listAdapter); 

		((Button) findViewById(R.id.btn_demande_moyens)).setOnClickListener(this);
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
		switch (v.getId()) {
			case R.id.btn_demande_moyens:
				android.util.Log.d(TAG, "CLICK JE DEMANDE");
				flag = ActionFlag.ADD;
				Vehicule veh = genVehicule();
		        IPictogram vehiculePicto = PictogramHolder.getInstance(this).getPictogram(PictogramHolder.RED_GRP);
		        touchedEntity = new Entity<Vehicule>(veh, vehiculePicto, EntityState.OFF_SITAC);
				observable.setChanged();
				observable.notifyObservers(MoyensActivity.this);
				break;
		}
			
	}
	
	@Override
	public void update() {
		List<IEntity> entities = controller.getInterventionEngine().getEntities();
		data.clear(); // parce qu'à chaque appel d'update() on récupère toute la liste d'entitées
		// et que la liste au deuxième appel d'update() a déjà été remplie, donc on la vide puis on la re-remplie
		// on pourrait penser aussi qu'un appel à update avec juste ce qui a changé serait mieux, j'vais réfléchir
		// sur une soluce qui va dans ce sens ;)
		Hashtable<String, String> map;
		for (int i=0; i<entities.size(); i++) {
			Vehicule v = (Vehicule) entities.get(i).getModel();
			map = new Hashtable<String, String>();
			map.put(DATA_IMG, ""+R.drawable.firetruck);
			map.put(DATA_TYPE, v.getName());
			map.put(DATA_CASERNE, v.getCaserneName());
			EtatVehicule ev = v.getEtat();
			map.put(DATA_ETAT, ev.toString());
			map.put(DATA_GHARR, v.getGroupesHoraires().get(EtatVehicule.SUR_LES_LIEUX));
			map.put(DATA_GHRET, v.getGroupesHoraires().get(EtatVehicule.DEMOBILISE));
			data.add(map);
		}
		listAdapter.notifyDataSetChanged();
	}
	
	public Vehicule genVehicule() {
		Vehicule veh = new Vehicule("42", Vehicule.randomCategorieVehicule().toString(), new Position(33.4, 48.8), "Caserne Beaulieu", Vehicule.randomEtatVehicule(), new Groupe("UniqueID", null, new ArrayList<Vehicule>()));
		return veh;
	}
//	
//	public class ItemAdapter extends BaseAdapter {
//
//		private List<IEntity> entities;
//
//	    public void setItems(List<IEntity> entities) {
//			this.entities = entities;
//		}
//
//		public int getCount() {
//	        if (entities == null) {
//	        	return 0;
//	        }
//	        return entities.size();
//	    }
//
//	    public Object getItem(int position) {
//	        if (entities == null) {
//	        	return null;
//	        }
//	        return entities.get(position);
//	    }
//
//	    public long getItemId(int position) {
//	        return 0;
//	    }
//
//	    // create a new View for each item referenced by the Adapter
//	    public View getView(int position, View convertView, ViewGroup parent) {
//	        View itemView = getLayoutInflater().inflate(R.layout.moyens_list_item, null);
//	        //View itemView = getLayoutInflater().inflate(R.layout.gridview_item, null);
//
//	        ((TextView) itemView.findViewById(R.id.vehicule_name)).setText(entities.get(position).getModel().getName());
//	        return itemView;
//	    }
//	}
//	
	
}

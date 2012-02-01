package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.agetac.R;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.tabs.MyActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MoyensActivity extends MyActivity implements OnItemClickListener {
	
	private static final String TAG = "MoyensActivity";
	
	private ItemAdapter itemAdapter;
	private ActionFlag flag;
	private IEntity touchedEntity;
	
	private ListView listView;

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
 
        //Creation d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();        
        map.put("titre", "VSAV Cleunay");
        map.put("description", "VSAV Rouge?");
        map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
  
        map = new HashMap<String, String>();
        map.put("titre", "FTP Janzé");
        map.put("description", "Arrivée: 0915");
        map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "FTP xxxx");
        map.put("description", "Arrivée: 0950");
        map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
 
        //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue moyens_list_item
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.moyens_list_item,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
        listView.setAdapter(mSchedule);
     
	}
	
	@Override
	public void onItemClick(AdapterView<?> adpt, View v, int index, long l) {
////		try {/*
//			final AdapterView<?> adapter = adpt;
//			final int position = index;
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
	public ActionFlag getActionFlag() {
		return flag;
	}
	
	@Override
	public IEntity getTouchedEntity() {
		return touchedEntity;
	}
	
	@Override
	public void update() {
		List<IEntity> entities = controller.getIntervention().getEntities();
		itemAdapter.setItems(entities);
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

	        ((TextView) itemView.findViewById(R.id.vehicule_name)).setText(entities.get(position).getName());
	        return itemView;
	    }
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}

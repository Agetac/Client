package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.agetac.R;
import org.agetac.model.ActionFlag;
import org.agetac.model.sign.IEntity;
import org.agetac.tabs.MyActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
// import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MoyensActivity extends MyActivity implements OnItemClickListener {
	
	private static final String TAG = "MoyensActivity";
	
	private ItemAdapter itemAdapter;
	private ActionFlag flag;
	private IEntity touchedEntity;
	
	private ListView maListViewPerso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moyens);
	    maListViewPerso = (ListView) findViewById(R.id.malistview);
	    maListViewPerso.setOnItemClickListener(this);
	    itemAdapter = new ItemAdapter();
	    maListViewPerso.setAdapter(itemAdapter);
	    
	  //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier affichageitem.xml
        map.put("titre", "VSAV Cleunay");
        //on ins�re un �l�ment description que l'on r�cup�rera dans le textView description cr�� dans le fichier affichageitem.xml
        map.put("description", "VSAV Rouge?");
        //on ins�re la r�f�rence � l'image (convertit en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.firetruck));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre ListView
 
        map = new HashMap<String, String>();
        map.put("titre", "FTP Janz�");
        map.put("description", "Arriv�e: 0915");
        map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "FTP xxxx");
        map.put("description", "Arriv�e: 0950");
        map.put("img", String.valueOf(R.drawable.firetruck));
        listItem.add(map);
 
        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.moyens_list_item,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
 
        //On attribut � notre listView l'adapter que l'on vient de cr�er
        maListViewPerso.setAdapter(mSchedule);
 
	    
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
	        View itemView = getLayoutInflater().inflate(R.layout.gridview_item, null);
	        ((TextView) itemView.findViewById(R.id.vehicule_name)).setText(entities.get(position).getName());
	        return itemView;
	    }
	}
}

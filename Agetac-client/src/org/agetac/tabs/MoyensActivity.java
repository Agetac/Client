package org.agetac.tabs;

import java.util.List;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.model.ActionFlag;
import org.agetac.model.Entity;
import org.agetac.observer.MyObservable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MoyensActivity extends Activity implements ITabActivity, OnItemClickListener {
	
	private Controller controller;
	private MyObservable observable;
	private ItemAdapter itemAdapter;
	private ActionFlag flag;
	private Entity touchedEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moyens);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setOnItemClickListener(this);
	    itemAdapter = new ItemAdapter();
	    gridview.setAdapter(itemAdapter);
		
		controller = Controller.getInstance();
		controller.addTabActivity(this);
		observable = new MyObservable();
		observable.addObserver(controller);
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
					ItemAdapter itemAdpt = (ItemAdapter) adapter.getAdapter();
					flag = ActionFlag.REMOVE;
					touchedEntity = (Entity) itemAdpt.getItem(position);
					observable.setChanged();
					observable.notifyObservers(MoyensActivity.this);
				}
			});
			confirmDelete.setNegativeButton(R.string.no, null);
			confirmDelete.show();
			
		} catch (ClassCastException e) {}
	}
	
	@Override
	public ActionFlag getActionFlag() {
		return flag;
	}
	
	@Override
	public Entity getTouchedEntity() {
		return touchedEntity;
	}
	
	@Override
	public void update() {
		List<Entity> entities = controller.getIntervention().getEntities();
		itemAdapter.setItems(entities);
		itemAdapter.notifyDataSetChanged();
	}
	
	public class ItemAdapter extends BaseAdapter {

		private List<Entity> entities;

	    public void setItems(List<Entity> entities) {
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

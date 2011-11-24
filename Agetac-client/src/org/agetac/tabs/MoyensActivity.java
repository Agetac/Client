package org.agetac.tabs;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.agetac.R;
import org.agetac.model.Intervention;
import org.agetac.model.Vehicule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MoyensActivity extends AbstractActivity implements OnItemClickListener {
	
	private ItemAdapter itemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moyens);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setOnItemClickListener(this);
	    itemAdapter = new ItemAdapter();
	    gridview.setAdapter(itemAdapter);
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
					intervention.removeVehicule((Vehicule) itemAdpt.getItem(position));
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
			itemAdapter.setVehicules(vehicules);
			itemAdapter.notifyDataSetChanged();
			
		} catch (ClassCastException e) {}
	}
	
	public class ItemAdapter extends BaseAdapter {

		private List<Vehicule> vehicules;

	    public void setVehicules(List<Vehicule> vehicules) {
			this.vehicules = vehicules;
		}

		public int getCount() {
	        if (vehicules == null) {
	        	return 0;
	        }
	        return vehicules.size();
	    }

	    public Object getItem(int position) {
	        if (vehicules == null) {
	        	return null;
	        }
	        return vehicules.get(position);
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new View for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View itemView = getLayoutInflater().inflate(R.layout.gridview_item, null);
	        ((TextView) itemView.findViewById(R.id.vehicule_name)).setText(vehicules.get(position).getName());
	        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>< "+vehicules.get(position).getName());
	        return itemView;
	    }
	}
}

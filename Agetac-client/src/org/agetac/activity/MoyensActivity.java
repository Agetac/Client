package org.agetac.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.agetac.R;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.PositionDTO;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDTO.VehicleType;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.common.dto.VehicleDemandDTO.DemandState;
import org.agetac.common.util.TimeFormatter;
import org.agetac.controller.Controller.ActionFlag;
import org.agetac.entity.EntityFactory;
import org.agetac.entity.EntityHolder;
import org.agetac.entity.IEntity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SimpleAdapter;

public class MoyensActivity extends AbstractActivity implements OnClickListener, OnMenuItemClickListener, OnItemClickListener {
	
	private static final String TAG			= "MoyensActivity";
	private static final String DATA_IMG		= "data_img";
	private static final String DATA_TYPE		= "data_type";
	private static final String DATA_CASERNE	= "data_caserne";
	private static final String DATA_ETAT		= "data_etat";
	private static final String DATA_GHDEM	= "data_gh_demande";
	private static final String DATA_GHARR	= "data_gh_arrivee";
	private static final String DATA_GHRET	= "data_gh_retour";
	
	private SimpleAdapter listAdapter;
	private ListView listView;
	private ArrayList<Hashtable<String, String>> data;
	private PopupMenu popupMenu;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moyens);
	    
		listView = (ListView) findViewById(R.id.moyens_listview);
	    
	    // Creation de l'ArrayList qui nous permettra de remplir la listView
        data = new ArrayList<Hashtable<String, String>>();
        
        //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue moyens_list_item
        listAdapter = new SimpleAdapter(this.getBaseContext(), data, R.layout.moyens_list_item,
               new String[] {DATA_IMG, DATA_TYPE, DATA_CASERNE, DATA_ETAT, DATA_GHDEM, DATA_GHARR, DATA_GHRET}, new int[] {R.id.img, R.id.type_vehicule, R.id.caserne_vehicule, R.id.etat_vehicule, R.id.gh_demande, R.id.gh_arrivee, R.id.gh_retour});
        listView.setAdapter(listAdapter);
        
        listView.setOnItemClickListener(this);

		((Button) findViewById(R.id.btn_demande_moyens)).setOnClickListener(this);
		
		popupMenu = new PopupMenu(MoyensActivity.this, findViewById(R.id.btn_demande_moyens));
		Menu menu = popupMenu.getMenu();
		MenuInflater inflater = popupMenu.getMenuInflater();
		inflater.inflate(R.menu.moyens_context_menu, menu);
		popupMenu.setOnMenuItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_demande_moyens:
				popupMenu.show();		
				break;
		}
			
	}

	@Override
	public void onItemClick(AdapterView<?> adpt, View v, int index, long l) {
		 try {
			 final List<IEntity> entities = controller.getEntities();
			 final int position = index;
			 final AlertDialog.Builder confirmDelete = new AlertDialog.Builder(this);
			 confirmDelete.setTitle(R.string.dialog_title_vehicule);
			 confirmDelete.setMessage(R.string.dialog_update_gh_ou_supprimer_vehicule);
			 confirmDelete.setPositiveButton(R.string.dialog_vehicule_supprimer, new
				 DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface dialog, int which) {
						 flag = ActionFlag.REMOVE;
						 touchedEntity = (IEntity) entities.get(position);
						 observable.setChanged();
						 observable.notifyObservers(MoyensActivity.this);
					}
				 });
			 confirmDelete.setNegativeButton(R.string.annuler, null);
			 confirmDelete.show();		
		 } catch (ClassCastException e) {
			 android.util.Log.e(TAG, e.getMessage());
		 }
		
	}	

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		VehicleDemandDTO dm;
		PopupMenu vehTypeMenu = new PopupMenu(MoyensActivity.this, findViewById(R.id.btn_demande_moyens));
		Menu menu = vehTypeMenu.getMenu();
		MenuInflater inflater = vehTypeMenu.getMenuInflater();
		vehTypeMenu.setOnMenuItemClickListener(this);
		
		switch (item.getItemId()) {
			case R.id.menu_alim:
				inflater.inflate(R.menu.alim, menu);
				vehTypeMenu.show();
				return true;
				
			case R.id.menu_attaque:
				inflater.inflate(R.menu.attaque, menu);
				vehTypeMenu.show();
				return true;

			case R.id.menu_secours:
				inflater.inflate(R.menu.secours, menu);
				vehTypeMenu.show();
				return true;
				
			case R.id.menu_risques:
				inflater.inflate(R.menu.risques, menu);
				vehTypeMenu.show();
				return true;
				
			case R.id.menu_commandement:
				inflater.inflate(R.menu.commandement, menu);
				vehTypeMenu.show();
				return true;
				
			case R.id.menu_cheminement:
				inflater.inflate(R.menu.cheminement, menu);
				vehTypeMenu.show();
				return true;
		
			case R.id.FPT:
				dm = genVehicleDemandDTO(VehicleType.FPT);
				break;
				
			case R.id.VSAV:
				dm = genVehicleDemandDTO(VehicleType.VSAV);
				break;

			case R.id.BEA:
				dm = genVehicleDemandDTO(VehicleType.BEA);
				break;
				
			case R.id.CAEM:
				dm = genVehicleDemandDTO(VehicleType.CAEM);
				break;
				
			case R.id.CCFM:
				dm = genVehicleDemandDTO(VehicleType.CCFM);
				break;
				
			case R.id.CCGC:
				dm = genVehicleDemandDTO(VehicleType.CCGC);
				break;
				
			case R.id.CCGCLC:
				dm = genVehicleDemandDTO(VehicleType.CCGCLC);
				break;
				
			case R.id.DA:
				dm = genVehicleDemandDTO(VehicleType.DA);
				break;
				
			case R.id.EMB:
				dm = genVehicleDemandDTO(VehicleType.EMB);
				break;
				
			case R.id.EPS:
				dm = genVehicleDemandDTO(VehicleType.EPS);
				break;
				
			case R.id.FMOGP:
				dm = genVehicleDemandDTO(VehicleType.FMOGP);
				break;
				
			case R.id.MPR:
				dm = genVehicleDemandDTO(VehicleType.MPR);
				break;
				
			case R.id.PEVSD:
				dm = genVehicleDemandDTO(VehicleType.PEVSD);
				break;
				
			case R.id.SAC_PS:
				dm = genVehicleDemandDTO(VehicleType.SAC_PS);
				break;

			case R.id.VCYNO:
				dm = genVehicleDemandDTO(VehicleType.VCYNO);
				break;
				
			case R.id.VICB:
				dm = genVehicleDemandDTO(VehicleType.VICB);
				break;
				
			case R.id.VL:
				dm = genVehicleDemandDTO(VehicleType.VL);
				break;
				
			case R.id.VLCC:
				dm = genVehicleDemandDTO(VehicleType.VLCC);
				break;
				
			case R.id.VLCG:
				dm = genVehicleDemandDTO(VehicleType.VLCG);
				break;
				
			case R.id.VLCGD:
				dm = genVehicleDemandDTO(VehicleType.VLCGD);
				break;
				
			case R.id.VLCS:
				dm = genVehicleDemandDTO(VehicleType.VLCS);
				break;
				
			case R.id.VLDP:
				dm = genVehicleDemandDTO(VehicleType.VLDP);
				break;
				
			case R.id.VLHR:
				dm = genVehicleDemandDTO(VehicleType.VLHR);
				break;
				
			case R.id.VLOS:
				dm = genVehicleDemandDTO(VehicleType.VLOS);
				break;
				
			case R.id.VLS:
				dm = genVehicleDemandDTO(VehicleType.VLS);
				break;
				
			case R.id.VLSV:
				dm = genVehicleDemandDTO(VehicleType.VLSV);
				break;
				
			case R.id.VNRBC:
				dm = genVehicleDemandDTO(VehicleType.VNRBC);
				break;
				
			case R.id.VPHV:
				dm = genVehicleDemandDTO(VehicleType.VPHV);
				break;
				
			case R.id.VPL:
				dm = genVehicleDemandDTO(VehicleType.VPL);
				break;
				
			case R.id.VPRO:
				dm = genVehicleDemandDTO(VehicleType.VPRO);
				break;
				
			case R.id.VRAD:
				dm = genVehicleDemandDTO(VehicleType.VRAD);
				break;
				
			case R.id.VRCB:
				dm = genVehicleDemandDTO(VehicleType.VRCB);
				break;
				
			case R.id.VSM:
				dm = genVehicleDemandDTO(VehicleType.VSM);
				break;
				
			case R.id.VSR:
				dm = genVehicleDemandDTO(VehicleType.VSR);
				break;
				
			case R.id.VTP:
				dm = genVehicleDemandDTO(VehicleType.VTP);
				break;
				
			case R.id.VTU:
				dm = genVehicleDemandDTO(VehicleType.VTU);
				break;
				
			default:
				// TODO récupérer le nom via un formulaire
				return false;
		}
		
		flag = ActionFlag.ADD;
		touchedEntity = EntityFactory.make(dm);
		android.util.Log.d(TAG, "DATA before ADDING: "+data.size());
		observable.setChanged();
		observable.notifyObservers(MoyensActivity.this);
		return super.onContextItemSelected(item);
	}
	
  
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moyens_context_menu, menu);
        menu.getItem(0).getSubMenu().setHeaderIcon(R.drawable.firetruck);
  
        return true;
     }
		
	@Override
	public void update() {
		List<IEntity> entities = controller.getEntities();
		data.clear();
		Collections.sort(entities);
		for (int i=0; i<entities.size(); i++) {
			IModel model = entities.get(i).getModel();
			// la map pour la prochaine ligne a ajouter
			Hashtable<String, String> map = new Hashtable<String, String>();
			
			if (model instanceof VehicleDTO) {
				VehicleDTO v = (VehicleDTO) model;
				map.put(DATA_IMG, ""+R.drawable.firetruck);
				String name = v.getType().name();
				if (v.getName() != null) {
					name += " "+v.getName();
				}
				map.put(DATA_TYPE, name);
				String caserne = getString(R.string.unknown);
				if (v.getBarrack().getName() != null) {
					caserne = v.getBarrack().getName();
				}
				map.put(DATA_CASERNE, caserne);
				map.put(DATA_ETAT, v.getState().name());
				map.put(DATA_GHDEM, TimeFormatter.getGroupeHoraire(v.getDemandTime())); // mapping GH non implemente sur le model
				map.put(DATA_GHARR, getString(R.string.unknown)); // mapping GH non implemente sur le model
				map.put(DATA_GHRET, getString(R.string.unknown)); // mapping GH non implemente sur le model
				// on ajoute la map a la liste
				data.add(map);
			
			} else if (model instanceof VehicleDemandDTO) {
				VehicleDemandDTO dm = (VehicleDemandDTO) model;
				// si la demande est en cours ou qu'elle a ete refusee on l'affiche
				// sinon ça signifie que le vehicule est deja affiche
				if (dm.getState() != DemandState.ACCEPTED) {
					map.put(DATA_IMG, ""+R.drawable.firetruck);
					String type = dm.getType().name();
					if (dm.getName() != null) {
						type += " "+dm.getName();
					}
					map.put(DATA_TYPE, type);
					map.put(DATA_CASERNE, getString(R.string.unknown));
					map.put(DATA_ETAT, dm.getState().name());
					map.put(DATA_GHDEM, TimeFormatter.getGroupeHoraire(dm.getGroupeHoraire())); // mapping GH non implemente sur le model
					map.put(DATA_GHARR, getString(R.string.unknown)); // mapping GH non implemente sur le model
					map.put(DATA_GHRET, getString(R.string.unknown)); // mapping GH non implemente sur le model
					// on ajoute la map a la liste
					data.add(map);
				}
			}
		}
		
		// on notifie l'adapter que le contenu de la liste a change
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listAdapter.notifyDataSetChanged();
			}
		});
	}
	
	public VehicleDemandDTO genVehicleDemandDTO(VehicleType type) {
		VehicleDemandDTO dm = new VehicleDemandDTO();
		dm.setType(type);
		dm.setState(DemandState.ASKED);
		dm.setGroupeHoraire(new Date());
		dm.setPosition(new PositionDTO());
		dm.getPosition().setKnown(false);
		return dm;
	}
}

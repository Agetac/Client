package org.agetac.tabs.impl;

import java.util.List;

import org.agetac.R;
import org.agetac.common.ActionFlag;
import org.agetac.entity.impl.Entity;
import org.agetac.entity.sign.IEntity;
import org.agetac.fragment.HiddenMenuFragment;
import org.agetac.fragment.OpenedMenuFragment;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.listener.IOnOverlayEventListener;
import org.agetac.model.impl.Agent;
import org.agetac.model.impl.Groupe;
import org.agetac.model.impl.Position;
import org.agetac.model.impl.Vehicule;
import org.agetac.model.impl.Vehicule.EtatVehicule;
import org.agetac.overlay.MapOverlay;
import org.agetac.pictogram.sign.IPictogram;
import org.agetac.tabs.sign.AbstractActivity;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class SITACActivity extends AbstractActivity implements IOnMenuEventListener, IOnOverlayEventListener, OnMenuItemClickListener {
	
	private static final String TAG = "SITACACtivity";
	private static final String EDIT_ITEM = "edit_item";
	
	private MapView mapView;
	private MapOverlay mapOverlay;
	private MapController mapCtrl;
	private FragmentManager fManager;
	private OpenedMenuFragment openedMenuFrag;
	private HiddenMenuFragment hiddenMenuFrag;
	private IPictogram currentPicto;
	private PopupMenu popupMenu;
	private AlertDialog editItemDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitac);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.MAPNIK);
		
		mapCtrl = mapView.getController();
		mapCtrl.setZoom(18); // you must call setZoom before setCenter
		
		GeoPoint geoP = new GeoPoint(48115436 ,-1638084);
		mapCtrl.setCenter(geoP);
		
		fManager = getFragmentManager();
		openedMenuFrag = (OpenedMenuFragment) fManager.findFragmentById(R.id.fragment_menu_opened);
		openedMenuFrag.setOnMenuEventListener(this);
		hiddenMenuFrag = (HiddenMenuFragment) fManager.findFragmentById(R.id.fragment_menu_hidden);
		hiddenMenuFrag.setOnMenuEventListener(this);
		fManager.beginTransaction().hide(hiddenMenuFrag).commit();
		
		mapOverlay = new MapOverlay(this);
		mapOverlay.setOnOverlayEventListener(this);
		mapView.getOverlays().add(mapOverlay);
		
		mapView.setBuiltInZoomControls(true);
		
		popupMenu = new PopupMenu(SITACActivity.this, findViewById(R.id.menu_anchor));
		Menu menu = popupMenu.getMenu();
		MenuInflater inflater = popupMenu.getMenuInflater();
		inflater.inflate(R.menu.sitac_context_menu, menu);
		popupMenu.setOnMenuItemClickListener(this);
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean(EDIT_ITEM, false)) {
				showEditItemDialog();
			}
		}
	}

	@Override
	public void update() {
		List<IEntity> entities = controller.getInterventionEngine().getEntities();
		mapOverlay.addEntities(entities);
		mapView.invalidate();
	}

	@Override
	public void onHideMenu() {
		FragmentTransaction fTransac = fManager.beginTransaction();
		fTransac.hide(openedMenuFrag);
		fTransac.show(hiddenMenuFrag);
		fTransac.commit();
	}

	@Override
	public void onShowMenu() {
		FragmentTransaction fTransac = fManager.beginTransaction();
		fTransac.hide(hiddenMenuFrag);
		fTransac.show(openedMenuFrag);
		fTransac.commit();
		openedMenuFrag.startShowMenuAnim();
	}

	@Override
	public void onPictogramSelected(IPictogram p) {
		this.currentPicto = p;
	}

	@Override
	public void onEntityLongPressed(final IEntity entity) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				touchedEntity = entity;
				popupMenu.show();
			}
		});
	}
	
	@Override
	public void onOverlayLongPressed(MotionEvent e, MapView mapView) {
		if (currentPicto != null) {
			GeoPoint m = (GeoPoint) mapView.getProjection().fromPixels(e.getX(), e.getY());
			Position p = new Position(m.getLongitudeE6(), m.getLatitudeE6());
			flag = ActionFlag.ADD;
			Vehicule v = new Vehicule("42", "FPT Janze", p, "", EtatVehicule.PARTIS, new Groupe("1", new Agent(), null));
			touchedEntity = new Entity<Vehicule>(v, currentPicto); //TODO vrai relation picto-Entity
			observable.setChanged();
			observable.notifyObservers(SITACActivity.this);
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_delete:
				flag = ActionFlag.REMOVE;
				observable.setChanged();
				observable.notifyObservers(SITACActivity.this);
				break;
				
			case R.id.menu_item_edit:
				showEditItemDialog();
				break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void showEditItemDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(SITACActivity.this);
			builder.setTitle(getString(R.string.dialog_title_edit_item, touchedEntity.getModel().getName()));
			LayoutInflater inflater = getLayoutInflater();
			final View dialogView = inflater.inflate(R.layout.edit_item_dialog, null);
			builder.setView(dialogView);
			final EditText nameField = (EditText) dialogView.findViewById(R.id.name);
			nameField.setHint(touchedEntity.getModel().getName());
			
			builder.setPositiveButton(R.string.save, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					flag = ActionFlag.REMOVE;
					observable.setChanged();
					observable.notifyObservers(SITACActivity.this);
					
					touchedEntity.getModel().setName(nameField.getText().toString().trim());
					flag = ActionFlag.ADD;
					observable.setChanged();
					observable.notifyObservers(SITACActivity.this);
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			editItemDialog = builder.create();
			editItemDialog.show();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (editItemDialog != null && editItemDialog.isShowing()) {
			outState.putBoolean(EDIT_ITEM, true);
			editItemDialog.dismiss();
		}
	}
}

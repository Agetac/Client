package org.agetac.tabs.impl;

import java.util.ArrayList;
import java.util.List;

import org.agetac.R;
import org.agetac.common.ActionFlag;
import org.agetac.entity.impl.Entity;
import org.agetac.entity.impl.Entity.EntityState;
import org.agetac.entity.sign.IEntity;
import org.agetac.fragment.HiddenMenuFragment;
import org.agetac.fragment.OpenedMenuFragment;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.listener.IOnOverlayEventListener;
import org.agetac.model.impl.Action;
import org.agetac.model.impl.Agent;
import org.agetac.model.impl.Groupe;
import org.agetac.model.impl.Position;
import org.agetac.model.impl.Vehicule;
import org.agetac.model.impl.Vehicule.EtatVehicule;
import org.agetac.overlay.MapOverlay;
import org.agetac.pictogram.PictogramGroup;
import org.agetac.pictogram.impl.Color;
import org.agetac.pictogram.impl.GraphicalOverload;
import org.agetac.pictogram.impl.LinePicto;
import org.agetac.pictogram.impl.Shape;
import org.agetac.pictogram.impl.State;
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
import android.graphics.Bitmap;
import android.graphics.Point;
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
	private GeoPoint lineBeginGeop;

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
		
		ArrayList<IEntity> offSitacEntities = new ArrayList<IEntity>();
		for (int i=0; i<entities.size(); i++) {
			if (entities.get(i).getState() == EntityState.OFF_SITAC) {
				offSitacEntities.add(entities.get(i));
			}
		}
		if (!offSitacEntities.isEmpty()) openedMenuFrag.addOffSitacEntities(offSitacEntities);
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
	public void onPictogramSelected(IPictogram p, PictogramGroup grp) {
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
		if (currentPicto != null && currentPicto.getShape() != Shape.LINEAR_SHAPE) {
			GeoPoint m = (GeoPoint) mapView.getProjection().fromPixels(e.getX(), e.getY());
			Position p = new Position(m.getLongitudeE6(), m.getLatitudeE6());
			flag = ActionFlag.ADD;
			Vehicule v = new Vehicule("42", "FPT Janze", p, "", EtatVehicule.PARTIS, new Groupe("1", new Agent(), null));
			touchedEntity = new Entity<Vehicule>(v, currentPicto, EntityState.ON_SITAC); //TODO vrai relation picto-Entity
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

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		return false;
	}

	@Override
	public boolean onUp(MotionEvent end, MapView mapView) {
		if (currentPicto != null && currentPicto.getShape()==Shape.LINEAR_SHAPE) {
			Point start, stop;
			Position lineBeginPos = new Position(lineBeginGeop.getLongitudeE6(), lineBeginGeop.getLatitudeE6());
			Point lineBeginPoint = mapView.getProjection().toMapPixels(lineBeginGeop, null);;
			
			GeoPoint me = (GeoPoint) mapView.getProjection().fromPixels(end.getX(), end.getY());
			stop = mapView.getProjection().toMapPixels(me, null);
			stop.set(stop.x-lineBeginPoint.x, stop.y-lineBeginPoint.y);
			
			start = mapView.getProjection().toMapPixels(lineBeginGeop, null);
			start.set(start.x-lineBeginPoint.x, start.y-lineBeginPoint.y);
			
			
			LinePicto lp = new LinePicto(currentPicto.getName(), currentPicto.getBitmap(), currentPicto.getColor(), currentPicto.getState(), currentPicto.getShape(), currentPicto.getGraphicalOverload(), start, stop);
			Action as = new Action("42", lineBeginPos);
			touchedEntity = new Entity<Action>(as, lp, EntityState.ON_SITAC); //TODO vrai relation picto-Entity
			flag = ActionFlag.ADD;
			observable.setChanged();
			observable.notifyObservers(SITACActivity.this);
			return true;
		}
		return false;
	}

	@Override
	public boolean lineBegin(MotionEvent start, MapView mapView) {
		if (currentPicto != null && currentPicto.getShape()==Shape.LINEAR_SHAPE) {
			lineBeginGeop = (GeoPoint) mapView.getProjection().fromPixels(start.getX(), start.getY());
			return true;
		}
		return false;
	}
}

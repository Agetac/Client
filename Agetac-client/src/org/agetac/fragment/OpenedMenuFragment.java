package org.agetac.fragment;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.entity.IEntity;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.view.Color;
import org.agetac.view.IPictogram;
import org.agetac.view.MenuExpandableListView;
import org.agetac.view.PictogramGroup;
import org.agetac.view.PictogramHolder;
import org.agetac.view.Shape;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;

public class OpenedMenuFragment extends Fragment implements IMenuFragment, OnClickListener, OnChildClickListener {

	private Animation hideMenuAnim;
	private Animation showMenuAnim;
	private IOnMenuEventListener listener;
	private ArrayList<PictogramGroup> groups;
	private ArrayList<IPictogram> pictosDangers, pictosMapItems, pictosMoyens, pictosOffSitac;
	private MenuExpandableListView menuAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideMenuAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
		hideMenuAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				if (listener != null) listener.onHideMenu();
			}
		});
		showMenuAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
		PictogramHolder pFactory = PictogramHolder.getInstance(getActivity());
		pictosDangers = pFactory.getPictograms(Shape.TRIANGLE_UP);
		pictosMapItems = pFactory.getPictograms(Color.BLACK);
		pictosMoyens = pFactory.getPictograms(Shape.SQUARE);
		pictosOffSitac = new ArrayList<IPictogram>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_opened, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((ImageButton) getActivity().findViewById(R.id.btn_hide_menu)).setOnClickListener(this);
		ExpandableListView listView = (ExpandableListView) getActivity().findViewById(R.id.menu);
		groups = new ArrayList<PictogramGroup>();
		
		// Groupe des vehicules en attente, TODO signalitique en cas de nouveau vehicule a placer
//		PictogramGroup waitingVehicles = new PictogramGroup("Véhicules en attente");
//		groups.add(waitingVehicles);
		
		// Groupe des dangers
		PictogramGroup grpDangers = new PictogramGroup(getString(R.string.dangers));
		grpDangers.setPictos(pictosDangers);
		groups.add(grpDangers);
		
		// Groupe des actions
//		PictogramGroup actions = new PictogramGroup("Actions");
//		// Pour l'instant, pour les tests, tous les pictos sont ajoutés dans le groupe actions
//		actions.setPictos(pictos);
//		groups.add(actions);
		
		// Groupe des moyens
		PictogramGroup grpMoyens = new PictogramGroup(getString(R.string.moyens));
		grpMoyens.setPictos(pictosMoyens);
		groups.add(grpMoyens);
		
		// Groupe des mapitems
		PictogramGroup grpMapItems = new PictogramGroup(getString(R.string.map_items));
		grpMapItems.setPictos(pictosMapItems);
		groups.add(grpMapItems);
		
		menuAdapter = new MenuExpandableListView(getActivity(), groups);
		listView.setAdapter(menuAdapter);
		listView.setOnChildClickListener(this);
		
		getView().startAnimation(showMenuAnim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_hide_menu:
			getView().startAnimation(hideMenuAnim);
			break;
			
			default:
				android.util.Log.d("OpenedMenuFragment", "onClick: "+v.toString());
				break;
		}
	}
	
	public void startShowMenuAnim() {
		getView().startAnimation(showMenuAnim);
	}

	@Override
	public void setOnMenuEventListener(IOnMenuEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void removeOnMenuEventListener() {
		this.listener = null;
	}

	@Override
	public boolean onChildClick(ExpandableListView p, View v, int grpIndex,
			int childIndex, long id) {

		v.setSelected(true);
		
		if (listener != null) {
			PictogramGroup grp = groups.get(grpIndex);
			IPictogram picto = grp.getPictos().get(childIndex);
			listener.onPictogramSelected(picto, grp);
		}
		return true;
	}

	public void addOffSitacEntities(ArrayList<IEntity> entities) {
		if (entities.isEmpty()) {
			if (groups.get(0).getGroupName().equals(getString(R.string.off_sitac))) {
				groups.remove(0);
			}
		} else {
			PictogramGroup grpOffSitac = null;
			if (groups.get(0).getGroupName().equals(getString(R.string.off_sitac))) {
				grpOffSitac = groups.get(0);
			
			} else {
				grpOffSitac = new PictogramGroup(getString(R.string.off_sitac));
				groups.add(0, grpOffSitac);
			}
			
			pictosOffSitac.clear();
			for (int i=0; i<entities.size(); i++) {
				pictosOffSitac.add(entities.get(i).getPictogram());
			}
			
			grpOffSitac.setPictos(pictosOffSitac);
			menuAdapter.notifyDataSetChanged();
		}
	}
}

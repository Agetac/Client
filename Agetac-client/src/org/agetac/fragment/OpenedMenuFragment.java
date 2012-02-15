package org.agetac.fragment;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.fragment.sign.IMenuFragment;
import org.agetac.listener.IOnMenuEventListener;
import org.agetac.menu.MenuExpandableListView;
import org.agetac.pictogram.PictogramFactory;
import org.agetac.pictogram.PictogramGroup;
import org.agetac.pictogram.sign.IPictogram;

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
	private ArrayList<IPictogram> pictos;
	
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
		PictogramFactory pFactory = PictogramFactory.getInstance(getActivity());
		pictos = pFactory.getPictograms();
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
		ArrayList<PictogramGroup> groups = new ArrayList<PictogramGroup>();
		
		// Groupe des vehicules en attente, TODO signalitique en cas de nouveau vehicule a placer
		PictogramGroup waitingVehicles = new PictogramGroup("Véhicules en attente");
		groups.add(waitingVehicles);
		
		// Groupe des actions
		PictogramGroup actions = new PictogramGroup("Actions");
		// Pour l'instant, pour les tests, tous les pictos sont ajoutés dans le groupe actions
		actions.setPictos(pictos);
		groups.add(actions);
		
		// Groupe des moyens
		groups.add(new PictogramGroup("Moyens"));
		groups.get(2).setPictos(pictos);
		
		MenuExpandableListView adapter = new MenuExpandableListView(getActivity(), groups);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(this);
		
		// Tests pour groupe des véhicules en attente "clignotant"
		//boolean test = false;
		//if(!test)
			//listView.getChildAt(0).setVisibility(View.INVISIBLE);
			//listView.getChildAt(0).setEnabled(false);
		//else
			//listView.getChildAt(0).setVisibility(View.VISIBLE);
		
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
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		if (listener != null) listener.onPictogramSelected(pictos.get(childPosition));
		return true;
	}
}

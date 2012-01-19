package org.agetac.fragment;

import org.agetac.R;
import org.agetac.fragment.sign.IMenuFragment;
import org.agetac.listener.IOnMenuEventListener;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class OpenedMenuFragment extends Fragment implements IMenuFragment, OnClickListener {

	private Animation hideMenuAnim;
	private Animation showMenuAnim;
	private IOnMenuEventListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideMenuAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.scroll_left);
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
		showMenuAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.scroll_right);
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
		getView().startAnimation(showMenuAnim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_hide_menu:
			getView().startAnimation(hideMenuAnim);
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
}

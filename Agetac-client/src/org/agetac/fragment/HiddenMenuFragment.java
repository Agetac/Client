package org.agetac.fragment;

import org.agetac.R;
import org.agetac.fragment.sign.IMenuFragment;
import org.agetac.listener.IOnMenuEventListener;

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

public class HiddenMenuFragment extends Fragment implements IMenuFragment, OnClickListener {

	private Animation showMenuAnim;
	private IOnMenuEventListener listener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_hidden, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((ImageButton) getActivity().findViewById(R.id.btn_show_menu)).setOnClickListener(this);
		showMenuAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.scroll_right);
		showMenuAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				if (listener != null) {
					System.out.println("will call onShowMenu");
					listener.onShowMenu();
				}
			}
		});
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_menu:
			getView().startAnimation(showMenuAnim);
			break;
		}
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

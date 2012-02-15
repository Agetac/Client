package org.agetac.fragment.sign;

import org.agetac.listener.IOnMenuEventListener;

public interface IMenuFragment {

	public void setOnMenuEventListener(IOnMenuEventListener listener);
	
	public void removeOnMenuEventListener();
}

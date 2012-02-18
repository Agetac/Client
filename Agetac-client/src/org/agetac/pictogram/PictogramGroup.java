package org.agetac.pictogram;

import java.util.ArrayList;

import org.agetac.pictogram.sign.IPictogram;

public class PictogramGroup {
	
	private String groupName;
	private ArrayList<IPictogram> pictos;

	public PictogramGroup(String name) {
		this.groupName = name;
		this.pictos = new ArrayList<IPictogram>();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<IPictogram> getPictos() {
		return pictos;
	}

	public void setPictos(ArrayList<IPictogram> pictos) {
		this.pictos = pictos;
	}

}

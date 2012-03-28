package org.agetac.handler;

import org.agetac.common.api.InterventionApi;
import org.agetac.common.exception.BadResponseException;
import org.agetac.common.model.impl.Action;
import org.agetac.common.model.impl.Cible;
import org.agetac.common.model.impl.DemandeMoyen;
import org.agetac.common.model.impl.Implique;
import org.agetac.common.model.impl.Source;
import org.agetac.common.model.impl.Vehicule;
import org.agetac.entity.EntityList;
import org.agetac.entity.IEntity;
import org.json.JSONException;

public class EditHandler implements IHandler {

	private EntityList entities;
	private InterventionApi iConn;
	
	public EditHandler(EntityList entList, InterventionApi iConn) {
		this.entities = entList;
		this.iConn = iConn;
	}

	@Override
	public void handle(IEntity entity) throws JSONException, BadResponseException {
		System.out.println("EditHandler: not implemented yet");
	}
}

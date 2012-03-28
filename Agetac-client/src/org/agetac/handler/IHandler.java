package org.agetac.handler;

import org.agetac.common.exception.BadResponseException;
import org.agetac.entity.IEntity;
import org.json.JSONException;

public interface IHandler {

	public void handle(IEntity e) throws JSONException, BadResponseException;
}

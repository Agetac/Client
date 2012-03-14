package org.agetac.exception;

import org.restlet.Response;

public class BadResponseException extends Exception {
	public BadResponseException(Response r){
		System.out.println("La requete n'a pas �t� un succ�s");
		System.out.println("Code : " + r.getStatus().getCode() +" "+ r.getStatus().getName());
		System.out.println("Description : "+ r.getStatus().getDescription());
	}
}
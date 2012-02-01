package org.agetac.tabs;

import org.agetac.R;
import org.agetac.controller.Controller;
import org.agetac.controller.MessagesController;
import org.agetac.model.ActionFlag;
import org.agetac.model.IEntity;
import org.agetac.model.Intervention;
import org.agetac.observer.MyObservable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;

public class MessagesActivity extends Activity implements ITabActivity, OnClickListener {

	private Controller controller;
	private MyObservable observable;
	private ActionFlag flag;
	private String message;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.messages);
		controller = Controller.getInstance();
		controller.addTabActivity(this);
		observable = new MyObservable();
		observable.addObserver(controller);

		findViewById(R.id.buttonEnvoyer).setOnClickListener(this);
		findViewById(R.id.buttonAnnuler).setOnClickListener(this);
		findViewById(R.id.buttonRetMess).setOnClickListener(this);


	}





	@Override
	public ActionFlag getActionFlag() {

		return flag;
	}

	@Override
	public IEntity getTouchedEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {


	}

	public String getMessage(){

		return message;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){

		case R.id.messAnnuler : 
			EditText et = (EditText)findViewById(R.id.edittext_message_normal);
			et.setText("");

			break;

		case R.id.messEnvoyer :

			EditText texteAEnvoyer = (EditText)findViewById(R.id.edittext_message_normal);
			message = texteAEnvoyer.getText().toString();

			flag = ActionFlag.SENDMESSAGE;

			texteAEnvoyer.setText("");
			observable.setChanged();
			observable.notifyObservers(MessagesActivity.this);		
			break;


		case R.id.buttonEnvoyer :

			EditText TexteJeSuis = (EditText)findViewById(R.id.edittext_je_suis);
			String jeSuis = TexteJeSuis.getText().toString();
			EditText TexteJeVois = (EditText)findViewById(R.id.edittext_je_vois);
			String jeVois = TexteJeVois.getText().toString();
			EditText TexteJePrevois = (EditText)findViewById(R.id.edittext_je_prevois);
			String jePrevois = TexteJePrevois.getText().toString();
			EditText TexteJeProcede = (EditText)findViewById(R.id.edittext_je_procede);
			String jeProcede = TexteJeProcede.getText().toString();
			EditText TexteJeDemande = (EditText)findViewById(R.id.edittext_je_demande);
			String jeDemande = TexteJeDemande.getText().toString();
			
			message = "Je suis : " + jeSuis + "/n" + 
					"Je vois : " + jeVois + "/n" +
					"Je prevois : " + jePrevois + "/n" +
					"Je procede : " + jeProcede + "/n" +
					"Je demande : " + jeDemande + "/n";
					
	

			flag = ActionFlag.SENDMESSAGE;

			TexteJeSuis.setText("");
			TexteJeVois.setText("");
			TexteJePrevois.setText("");
			TexteJeProcede.setText("");
			TexteJeDemande.setText("");
			
			observable.setChanged();
			observable.notifyObservers(MessagesActivity.this);		
			break;

		case R.id.buttonAnnuler : 

			EditText jeSuisA = (EditText)findViewById(R.id.edittext_je_suis);
			jeSuisA.setText("");
			EditText jeVoisA = (EditText)findViewById(R.id.edittext_je_vois);
			jeVoisA.setText("");
			EditText jePrevoisA = (EditText)findViewById(R.id.edittext_je_prevois);
			jePrevoisA.setText("");
			EditText jeProcedeA = (EditText)findViewById(R.id.edittext_je_procede);
			jeProcedeA.setText("");
			EditText jeDemandeA = (EditText)findViewById(R.id.edittext_je_demande);
			jeDemandeA.setText("");

			break;

		case R.id.retMessAmb : 

			setContentView(R.layout.messages);
			findViewById(R.id.buttonEnvoyer).setOnClickListener(this);
			findViewById(R.id.buttonAnnuler).setOnClickListener(this);
			findViewById(R.id.buttonRetMess).setOnClickListener(this);
			break;


		case R.id.buttonRetMess : 

			setContentView(R.layout.mess);
			findViewById(R.id.messEnvoyer).setOnClickListener(this);
			findViewById(R.id.messAnnuler).setOnClickListener(this);
			findViewById(R.id.retMessAmb).setOnClickListener(this);
			break;

		}
	}

}








package org.agetac.activity;

import org.agetac.R;
import org.agetac.command.SendMessageCommand;
import org.agetac.common.ActionFlag;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MessagesActivity extends AbstractActivity implements OnClickListener {

	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messages);

		findViewById(R.id.buttonEnvoyer).setOnClickListener(this);
		findViewById(R.id.buttonAnnuler).setOnClickListener(this);
		findViewById(R.id.buttonRetMess).setOnClickListener(this);
		findViewById(R.id.buttonConsMess).setOnClickListener(this);
	}

	@Override
	public void update() {
		
	}

	@Override
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

			flag = ActionFlag.SEND_MESSAGE;

			texteAEnvoyer.setText("");
			observable.setChanged();
			observable.notifyObservers(MessagesActivity.this);

			if(SendMessageCommand.getMessOk()){

				AlertDialog.Builder confirmSend = new AlertDialog.Builder(this);

				confirmSend.setMessage("Message envoye");
				confirmSend.setNeutralButton("OK", null);
				confirmSend.show();
				SendMessageCommand.setMessOk(false);

			}

			else {
				AlertDialog.Builder confirmSend = new AlertDialog.Builder(this);
				confirmSend.setMessage("Echec de l'envoi");
				confirmSend.setNeutralButton("Ok", null);
				confirmSend.show();
			}
		

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



		flag = ActionFlag.SEND_MESSAGE;

		TexteJeSuis.setText("");
		TexteJeVois.setText("");
		TexteJePrevois.setText("");
		TexteJeProcede.setText("");
		TexteJeDemande.setText("");

		observable.setChanged();
		observable.notifyObservers(MessagesActivity.this);	

		AlertDialog.Builder confirmSendAmb = new AlertDialog.Builder(this);

		confirmSendAmb.setMessage("Message envoye");

		confirmSendAmb.setNeutralButton("OK", null);

		confirmSendAmb.show();

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
		findViewById(R.id.buttonConsMess).setOnClickListener(this);
		break;


	case R.id.buttonRetMess : 

		setContentView(R.layout.mess);
		findViewById(R.id.messEnvoyer).setOnClickListener(this);
		findViewById(R.id.messAnnuler).setOnClickListener(this);
		findViewById(R.id.retMessAmb).setOnClickListener(this);
		findViewById(R.id.buttonConsMess).setOnClickListener(this);
		break;



	case R.id.buttonConsMess : 

		setContentView(R.layout.reception_mess);
		findViewById(R.id.retMessAmb).setOnClickListener(this);
		findViewById(R.id.buttonRetMess).setOnClickListener(this);

		break;

	}
}

}







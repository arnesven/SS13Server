package model.actions;

import model.Client;
import model.map.Room;

public class ClientActionPerformer implements ActionPerformer {

	private Client client;

	public ClientActionPerformer(Client cl) {
		this.client = cl;
	}
	
	@Override
	public Room getPosition() {
		return client.getPosition();
	}

	@Override
	public void addTolastTurnInfo(String string) {
		client.addTolastTurnInfo(string);
	}

	@Override
	public boolean isInfected() {
		return client.isInfected();
	}

	@Override
	public String getPublicName() {
		return client.getCharacterPublicName();
	}

	@Override
	public boolean isClient(Client cl) {
		return client == cl;
	}

}

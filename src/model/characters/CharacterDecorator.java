package model.characters;

import model.Client;
import model.map.Room;


public abstract class CharacterDecorator extends GameCharacter {


	private GameCharacter innerChar;
	
	
	public CharacterDecorator(GameCharacter chara, String name) {
		super("name");
		innerChar = chara;
	}

	protected GameCharacter getInner() {
		return innerChar;
	}
	
	@Override
	public String getBaseName() {
		return innerChar.getBaseName();
	}
	
	@Override
	public String getPublicName() {
		return innerChar.getPublicName();
	}
	
	@Override
	public String getFullName() {
		return innerChar.getFullName();
	}
	
	@Override
	public int getStartingRoom() {
		return innerChar.getStartingRoom();
	}
	
	@Override 
	public Client getClient() {
		return innerChar.getClient();
	}
	
	@Override
	public void setClient(Client c) {
		innerChar.setClient(c);
	}
	
	@Override
	public Room getPosition() {
		return innerChar.getPosition();
	}
	
	@Override
	public void setPosition(Room room) {
		innerChar.setPosition(room);
	}
	
	@Override
	public boolean checkInstance(InstanceChecker infectChecker) {
		return innerChar.checkInstance(infectChecker);
	}
	
	
}

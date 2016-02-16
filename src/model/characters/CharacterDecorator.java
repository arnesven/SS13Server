package model.characters;

import model.Client;


public abstract class CharacterDecorator extends GameCharacter {


	private GameCharacter innerChar;
	
	
	public CharacterDecorator(GameCharacter chara, String name) {
		super("name");
		innerChar = chara;
	}

	@Override
	public String getName() {
		return innerChar.getName();
	}
	
	@Override
	public String getRealName() {
		return innerChar.getName();
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
	

}

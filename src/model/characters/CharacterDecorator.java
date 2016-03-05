package model.characters;

import java.util.List;

import model.Player;
import model.items.GameItem;
import model.map.Room;


/**
 * @author erini02
 * Superclass for character "decorators". A decorator is
 * a class which envelopes the game character. E.g. the host
 * decorator indicates that this character is a host. Other possible
 * decorators could be: "invisible", "burning", "wearing clown clothes".
 */
public abstract class CharacterDecorator extends GameCharacter {


	private GameCharacter innerChar;
	
	
	public CharacterDecorator(GameCharacter chara, String name) {
		super("name", chara.getStartingRoom(), chara.getSpeed());
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
	public Player getClient() {
		return innerChar.getClient();
	}
	
	@Override
	public void setClient(Player c) {
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
	
	@Override
	public List<GameItem> getItems() {
		return innerChar.getItems();
	}
	
	@Override
	public boolean isCrew() {
		return innerChar.isCrew();
	}
}

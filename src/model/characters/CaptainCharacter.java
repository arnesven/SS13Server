package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.Grenade;
import model.items.KeyCard;

public class CaptainCharacter extends GameCharacter {

	public CaptainCharacter() {
		super("Captain", 20, 16.0);
	}
	
	@Override
	public List<GameItem> getStartingItems(){
		ArrayList<GameItem> list = new ArrayList<GameItem>();
		list.add(new KeyCard());
		return list;
	}

}

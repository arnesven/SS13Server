package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.suits.FireSuit;
import model.items.weapons.Grenade;

public class CaptainCharacter extends GameCharacter {

	public CaptainCharacter() {
		super("Captain", 20, 16.0);
	}
	
	@Override
	public List<GameItem> getStartingItems(){
		ArrayList<GameItem> list = new ArrayList<GameItem>();
		list.add(new KeyCard());
		list.add(new FireSuit());
		return list;
	}

}

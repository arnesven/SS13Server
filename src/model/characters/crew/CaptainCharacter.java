package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.items.general.NuclearDisc;
import model.items.suits.CaptainsOutfit;

public class CaptainCharacter extends CrewCharacter {

	public CaptainCharacter() {
		super("Captain", 20, 16.0);
        removeSuit();
        putOnSuit(new CaptainsOutfit(this));
	}
	
	@Override
	public List<GameItem> getStartingItems(){
		ArrayList<GameItem> list = new ArrayList<GameItem>();
		list.add(new NuclearDisc());
		list.add(new KeyCard());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new CaptainCharacter();
	}

}

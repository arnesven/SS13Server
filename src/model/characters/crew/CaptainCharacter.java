package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.CosmicArtifact;
import model.items.foods.SpaceRum;
import model.items.foods.Vodka;
import model.items.general.*;
import model.items.suits.CaptainsOutfit;

public class CaptainCharacter extends CrewCharacter {

	public CaptainCharacter() {
		super("Captain", 20, 16.0);
        removeSuit();
        putOnSuit(new CaptainsOutfit(this));
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<GameItem>();
		list.add(new KeyCard());

		return list;
	}

	@Override
	public GameCharacter clone() {
		return new CaptainCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 50;
    }
}

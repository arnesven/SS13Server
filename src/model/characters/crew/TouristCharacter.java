package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.visitors.VisitorCharacter;
import model.items.suits.SunGlasses;
import util.MyRandom;
import model.GameData;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.FancyClothes;
import model.map.NukieShipRoom;
import model.map.Room;

public class TouristCharacter extends VisitorCharacter {

	public TouristCharacter() {
		super("Tourist", 0, 1.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		List<GameItem> l = new ArrayList<GameItem>();
		l.add(new FancyClothes());
        l.add(new SunGlasses());
		return l;
	}
	


	@Override
	public GameCharacter clone() {
		return new TouristCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 850;
    }
}

package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.GeigerMeter;
import model.items.general.Teleporter;

public class ScienceOfficerCharacter extends CrewCharacter {

	public ScienceOfficerCharacter() {
		super("Science Officer", 1, 9.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new GeigerMeter());
        list.add(new Teleporter());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new ScienceOfficerCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 250;
    }
}

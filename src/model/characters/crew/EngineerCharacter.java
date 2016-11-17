package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.ElectronicParts;
import model.items.general.GameItem;
import model.items.general.PowerRadio;
import model.items.general.Tools;
import model.items.suits.FireSuit;

public class EngineerCharacter extends CrewCharacter {

	public EngineerCharacter() {
		super("Engineer", 26, 10.0);
	}


    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new FireSuit());
		list.add(new Tools());
		list.add(new PowerRadio());
        list.add(new ElectronicParts());
        return list;
	}


	@Override
	public GameCharacter clone() {
		return new EngineerCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 150;
    }
}

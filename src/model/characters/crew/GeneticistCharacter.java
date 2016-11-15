package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Syringe;

public class GeneticistCharacter extends CrewCharacter {

	public GeneticistCharacter() {
		super("Geneticist", 1, 8.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Syringe());
		list.add(new MedKit());
		list.add(new Chemicals());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new GeneticistCharacter();
	}


    @Override
    public int getStartingMoney() {
        return 100;
    }
}

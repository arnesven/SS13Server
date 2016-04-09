package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;

public class GeneticistCharacter extends CrewCharacter {

	public GeneticistCharacter() {
		super("Geneticist", 1, 8.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Chemicals());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new GeneticistCharacter();
	}

}

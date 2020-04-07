package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.foods.Beer;
import model.items.foods.Vodka;
import model.items.foods.Wine;
import model.items.general.GameItem;
import model.items.weapons.Shotgun;

public class BartenderCharacter extends CrewCharacter {

	public BartenderCharacter() {
		super("Bartender", SUPPORT_TYPE, 10, 4.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Shotgun());
        list.add(new Beer());
        list.add(new Beer());
        list.add(new Vodka());
        list.add(new Wine());
		return list;
	}


    @Override
	public GameCharacter clone() {
		return new BartenderCharacter();
	}

	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "The crew is always thirsty." +
				" Your job is to serve them drinks and listen to their stories.", "Refrigerator Access").makeString();
	}
}

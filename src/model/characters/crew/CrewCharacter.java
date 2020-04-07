package model.characters.crew;

import model.characters.general.HumanCharacter;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.items.suits.OutFit;

import java.util.ArrayList;
import java.util.List;

public abstract class CrewCharacter extends HumanCharacter {

	public CrewCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
		putOnEquipment(new OutFit(this));
	}


    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> items = new ArrayList<>();
        items.add(new MoneyStack(getStartingMoney()));
        items.addAll(getCrewSpecificItems());
        return items;
    }

    public int getStartingMoney() {
        return 5;
    }

    public abstract List<GameItem> getCrewSpecificItems();

    @Override
    public boolean isCrew() {
        return true;
    }


    public String getJobDescription() {return "Stuff";}
}

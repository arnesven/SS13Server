package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Target;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.ChefsHat;
import model.items.weapons.Knife;
import model.objects.general.CookOMatic;

public class ChefCharacter extends CrewCharacter {

	public ChefCharacter() {
		super("Chef", SUPPORT_TYPE, 8, 5.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Knife());
		list.add(new ChefsHat());
		return list;
	}
	
	@Override
	public void giveItem(GameItem it, Target giver) {
		if (giver instanceof CookOMatic) {
			super.giveItem(it.clone(), giver);
		}
		super.giveItem(it, giver);
	}

	@Override
	public GameCharacter clone() {
		return new ChefCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 50;
    }

	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "Something's cooking! Keep those crewmembers fed." +
				" The station's kitchen is well stocked so just keep churning out the pizzas, burgers and pies.",
				"Double Serving").makeString();
	}
}

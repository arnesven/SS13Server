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
		super("Chef", 8, 5.0);
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
}

package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Target;
import model.characters.GameCharacter;
import model.items.FireExtinguisher;
import model.items.GameItem;
import model.items.MedKit;
import model.items.suits.ChefsHat;
import model.items.weapons.Knife;
import model.objects.CookOMatic;

public class ChefCharacter extends GameCharacter {

	public ChefCharacter() {
		super("Chef", 8, 5.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
//		list.add(new FireExtinguisher());
		list.add(new Knife());
		list.add(new ChefsHat());
		return list;
	}
	
	@Override
	public void giveItem(GameItem it, Target giver) {
		if (giver instanceof CookOMatic) {
			super.giveItem(it, giver);
		}
		super.giveItem(it, giver);
	}

}

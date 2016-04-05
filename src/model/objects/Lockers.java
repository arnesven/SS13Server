package model.objects;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.ActionOption;
import model.items.GameItem;
import model.items.MedKit;
import model.items.suits.OutFit;
import model.map.Room;
import model.modes.GameMode;
import model.actions.SensoryLevel;

public class Lockers extends ContainerObject {

	public Lockers(Room position) {
		super("Lockers", position);
		getInventory().add(new MedKit());
		
		//add some random outfits.
		do {
			getInventory().add(new OutFit(MyRandom.sample(GameMode.getAllCrewAsStrings())));
		} while (MyRandom.nextDouble() < 0.33);
		
	}
	
	@Override
	protected char getIcon(Player whosAsking) {
		return 'l';
	}

	

}

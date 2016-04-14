package model.objects.general;

import util.MyRandom;
import model.Player;
import model.items.general.MedKit;
import model.items.suits.OutFit;
import model.map.Room;
import model.modes.GameMode;

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

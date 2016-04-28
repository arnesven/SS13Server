package model.objects.general;

import graphics.sprites.Sprite;
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
			getInventory().add(new OutFit(MyRandom.sample(GameMode.getAllCrew())));
		} while (MyRandom.nextDouble() < 0.33);
		
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("lockers", "closet.png", 0);
    }
}

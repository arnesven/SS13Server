package model.actions.general;

import java.util.List;

import model.Actor;
import model.GameData;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.objects.general.GameObject;
import model.objects.general.HiveObject;
import model.items.general.HidableItem;
import util.MyRandom;


public class SearchAction extends Action {

    private static final double RANDOM_ITEM_CHANCE = 0.05;

    public SearchAction() {
		super("Search", SensoryLevel.PHYSICAL_ACTIVITY);
	}


	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		boolean foundSomething = false;
		for (GameObject o : performingClient.getPosition().getObjects()) {
			if (o instanceof HiveObject) {
				if (!performingClient.isInfected()) {
					((HiveObject)o).setFound(true);
					performingClient.addTolastTurnInfo("You found the hive! All humans will now also see it while standing in this room.");
					foundSomething = true;
				} else {
					performingClient.addTolastTurnInfo("The hive is here, but why tell anybody?");
					foundSomething = true;
				}
			}
		}
		
		for (GameItem it : performingClient.getPosition().getItems()) {
			if (it instanceof HidableItem) {
				((HidableItem)it).setHidden(false);
				performingClient.addTolastTurnInfo(BombItem.FOUND_A_BOMB_STRING);
				foundSomething = true;
			}
		}

        if (MyRandom.nextDouble() <= RANDOM_ITEM_CHANCE) {
            GameItem it = MyRandom.sample(MyRandom.getItemsWhichAppearRandomly());
            performingClient.addTolastTurnInfo("You found a " + it.getBaseName() + ".");
            performingClient.getCharacter().giveItem(it, null);
            foundSomething = true;
        }
		
		if (!foundSomething) {
			performingClient.addTolastTurnInfo("Nothing of interest here...");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { // Not needed	
	}


	@Override
	protected String getVerb(Actor whosAsking) {
		return "searched";
	}

}

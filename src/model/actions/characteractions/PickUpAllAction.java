package model.actions.characteractions;

import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;

public class PickUpAllAction extends Action {

	public PickUpAllAction() {
		super("Pick Up All", SensoryLevel.PHYSICAL_ACTIVITY);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Cleaned the room";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		while (performingClient.getPosition().getItems().size() > 0) {
			GameItem item = performingClient.getPosition().getItems().get(0);
			performingClient.getPosition().getItems().remove(item);
			performingClient.getCharacter().giveItem(item, null);
		}
		performingClient.addTolastTurnInfo("You cleaned the room of items.");
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		
	}

	@Override
	public Sprite getAbilitySprite() {
		return new Sprite("pickupallability", "interface.png", 11, 11, null);
	}
}

package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.items.GameItem;
import model.items.NuclearDisc;
import model.items.suits.SpaceSuit;
import model.map.AirLockRoom;
import model.npcs.NPC;
import model.actions.SensoryLevel;
import model.modes.InfiltrationGameMode;

public class OperativeCharacter extends GameCharacter {

	public OperativeCharacter(int num, int startRoom) {
		super("Operative #" + num, startRoom, 12.5);
	}

	@Override
	public List<GameItem> getStartingItems() {
		List<GameItem> gi = new ArrayList<>();
		return gi;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		if (getPosition() instanceof AirLockRoom && hasASpaceSuitOn()) {
			at.add(new Action("Leave SS13", SensoryLevel.PHYSICAL_ACTIVITY) {
				
				@Override
				public void setArguments(List<String> args, Actor performingClient) { }
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					((Player)performingClient).moveIntoRoom(gameData.getRoom("Nuclear Ship"));
					if (hasTheDisk(performingClient)) {
						((InfiltrationGameMode)gameData.getGameMode()).setNuked(true);
					}
				}
			});
		}
	}

	protected boolean hasTheDisk(Actor performingClient) {
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof NuclearDisc) {
				return true;
			}
		}
		return false;
	}

	private boolean hasASpaceSuitOn() {
		return getSuit() instanceof SpaceSuit;
	}


}

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
import model.actions.characteractions.EscapeAndSetNukeAction;
import model.modes.InfiltrationGameMode;
import model.items.weapons.Revolver;

public class OperativeCharacter extends GameCharacter {

	public OperativeCharacter(int num, int startRoom) {
		super("Operative #" + num, startRoom, 17.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		List<GameItem> gi = new ArrayList<>();
		gi.add(new Revolver());
		return gi;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		if (getPosition() instanceof AirLockRoom && hasASpaceSuitOn()) {
			at.add(new EscapeAndSetNukeAction());
		}
	}


	private boolean hasASpaceSuitOn() {
		return getSuit() instanceof SpaceSuit;
	}


}

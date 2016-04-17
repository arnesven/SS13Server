package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.suits.SpaceSuit;
import model.map.AirLockRoom;
import model.actions.characteractions.EscapeAndSetNukeAction;
import model.actions.characteractions.StealAction;
import model.items.weapons.Revolver;

public class OperativeCharacter extends HumanCharacter {

	private int num;

	public OperativeCharacter(int num, int startRoom) {
		super("Operative #" + num, startRoom, 17.0);
		this.num = num;
	}

	@Override
	public List<GameItem> getStartingItems() {
		List<GameItem> gi = new ArrayList<>();
		gi.add(new Revolver());
		return gi;
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		if (getPosition() instanceof AirLockRoom && hasASpaceSuitOn()) {
			at.add(new EscapeAndSetNukeAction());
		}
		Action stealAction = new StealAction(this.getActor());
		if (stealAction.getOptions(gameData, this.getActor()).numberOfSuboptions() > 0) {
			at.add(stealAction);
		}
	}


	private boolean hasASpaceSuitOn() {
		return getSuit() instanceof SpaceSuit;
	}

	@Override
	public GameCharacter clone() {
		return new OperativeCharacter(num, this.getStartingRoom());
	}


}

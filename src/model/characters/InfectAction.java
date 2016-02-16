package model.characters;

import java.util.List;

import model.Client;
import model.GameData;
import model.GameItem;
import model.actions.Action;
import model.actions.Target;
import model.actions.TargetingAction;

public class InfectAction extends TargetingAction {

	public InfectAction(Client cl) {
		super("Infect", cl);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Client performingClient, Target target, GameItem item) {
		//TODO : implement this action
		
	}

	
}

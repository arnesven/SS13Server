package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.general.Laptop;

public class JackInAction extends Action {

	private Laptop pc;

	public JackInAction(Laptop pc) {
		super("Jack in", SensoryLevel.OPERATE_DEVICE);
		this.pc = pc;
		
		
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Jacked in";
	}
	



	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (!performingClient.getItems().contains(pc)) {
			performingClient.addTolastTurnInfo("What? The laptop is gone! Your action failed.");
		}
		
		pc.setJackRoom(performingClient.getPosition());
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) { }

	
	
}

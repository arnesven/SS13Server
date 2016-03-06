package model.npcs;


import util.MyRandom;
import model.GameData;
import model.actions.Action;
import model.actions.MeowingAction;


public class SpontaneousAct implements ActionBehavior {

	private double probability;
	private Action action;

	public SpontaneousAct(double prob, Action act) {
		this.probability = prob;
		this.action = act;
	}

	@Override
	public void act(NPC npc, GameData gameData) {
		if (MyRandom.nextDouble() < probability) {
			doTheAction(gameData, npc);
		}
	}

	protected void doTheAction(GameData gameData, NPC npc) {
		this.action.doTheAction(gameData, npc);		
	}

}

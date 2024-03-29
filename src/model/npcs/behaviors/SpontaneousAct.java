package model.npcs.behaviors;


import model.Actor;
import util.MyRandom;
import model.GameData;
import model.actions.general.Action;
import model.npcs.NPC;


public class SpontaneousAct implements ActionBehavior {

	private double probability;
	private Action action;

	public SpontaneousAct(double prob, Action act) {
		this.probability = prob;
		this.action = act;
	}

	@Override
	public void act(Actor npc, GameData gameData) {
		if (MyRandom.nextDouble() < probability) {
			doTheAction(gameData, npc);
		}
	}

	protected void doTheAction(GameData gameData, Actor npc) {
		this.action.doTheAction(gameData, npc);		
	}

}

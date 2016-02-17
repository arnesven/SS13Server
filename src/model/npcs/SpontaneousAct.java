package model.npcs;

import java.util.Random;

import model.GameData;
import model.actions.Action;
import model.actions.MeowingAction;
import model.actions.NPCActionPerformer;


public class SpontaneousAct implements ActionBehavior {

	private double probability;
	private Action action;
	private static Random random = new Random();

	public SpontaneousAct(double prob, Action act) {
		this.probability = prob;
		this.action = act;
	}

	@Override
	public void act(NPC npc, GameData gameData) {
		if (random.nextDouble() < probability) {
			this.action.printAndExecute(gameData, new NPCActionPerformer(npc));
		}
	}

}

package model.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.npcs.NPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class Crazyness extends Event {

	private List<NPC> crazyPeople = new ArrayList<>();

	public double getProbability() {
		return 0.01;
	}

	@Override
	public void apply(GameData gameData) {

		if (MyRandom.nextDouble() < getProbability()) {
			List<NPC> npclist = new ArrayList<>();
			npclist.addAll(gameData.getNPCs());
			Collections.shuffle(npclist);


			for (NPC npc : npclist) {
				if (npc.getCharacter().isCrew()) {
					npc.setActionBehavior(new CrazyBehavior());
					npc.setMoveBehavior(new MeanderingMovement(0.80));
					crazyPeople.add(npc);
					break;
				}
			}
		}
	}
	
	public List<NPC> getCrazyPeople() {
		return crazyPeople;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}

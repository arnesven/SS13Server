package model.events.ambient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.events.Event;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.npcs.NPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class SpontaneousCrazyness extends AmbientEvent {

	private List<NPC> crazyPeople = new ArrayList<>();
    private static double occurrenceChance = everyNGames(8);

    @Override
    protected double getStaticProbability() {
        return occurrenceChance;
    }


    @Override
	public void apply(GameData gameData) {

		if (MyRandom.nextDouble() < getProbability()) {
			List<NPC> npclist = new ArrayList<>();
			npclist.addAll(gameData.getNPCs());
			Collections.shuffle(npclist);


			for (NPC npc : npclist) {
				if (npc.getCharacter().isCrew() && !npc.isDead() && !crazyPeople.contains(npc)) {
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

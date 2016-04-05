package model.npcs.behaviors;

import model.npcs.NPC;

public class MeanderingHumanMovement extends MeanderingMovement {

	public MeanderingHumanMovement(double probability) {
		super(probability);
	}
	
	@Override
	public void move(NPC npc) {
		double old = this.getProbability();
		if (npc.getPosition().hasFire() || npc.getPosition().hasHullBreach()) {
			this.setProbability(1.0);
		} 
		super.move(npc);
		this.setProbability(old);
	}


}

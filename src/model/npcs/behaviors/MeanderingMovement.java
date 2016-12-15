package model.npcs.behaviors;

import java.util.List;

import util.Logger;
import util.MyRandom;
import model.map.rooms.Room;
import model.npcs.NPC;


public class MeanderingMovement implements MovementBehavior {

	private double probability;

	/**
	 * Constructor for this behavior.
	 * @param probability the probability of a move.
	 */
	public MeanderingMovement(double probability) {
		this.probability = probability;
	}

	@Override
	public void move(NPC npc) {
		if (MyRandom.nextDouble() < probability) {
			List<Room> listOfNeighboringRooms = npc.getPosition().getNeighborList();
            if (listOfNeighboringRooms.size() > 0) {
                Room dest = MyRandom.sample(listOfNeighboringRooms);
                npc.moveIntoRoom(dest);
                Logger.log(npc.getName() + " is now in " + dest.getName());
            }
		}
	}
	
	protected void setProbability(double d) {
		probability = d;
	}

	protected double getProbability() {
		return probability;
	}

}

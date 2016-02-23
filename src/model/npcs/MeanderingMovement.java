package model.npcs;

import java.util.List;

import util.MyRandom;
import model.map.Room;


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
			Room dest = listOfNeighboringRooms.get(MyRandom.nextInt(listOfNeighboringRooms.size()));
			npc.moveIntoRoom(dest);
			System.out.println("Cat is now in " + dest.getName());
		}
	}

}

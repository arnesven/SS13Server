package model.npcs;

import java.util.List;
import java.util.Random;

import model.map.Room;


public class MeanderingMovement implements MovementBehavior {

	private double probability;
	private static Random random = new Random();

	/**
	 * Constructor for this behavior.
	 * @param probability the probability of a move.
	 */
	public MeanderingMovement(double probability) {
		this.probability = probability;
	}

	@Override
	public void move(NPC npc) {
		if (random.nextDouble() < probability) {
			List<Room> listOfNeighboringRooms = npc.getPosition().getNeighborList();
			Room dest = listOfNeighboringRooms.get(random.nextInt(listOfNeighboringRooms.size()));
			npc.moveIntoRoom(dest);
			System.out.println("Cat is now in " + dest.getName());
		}
	}

}

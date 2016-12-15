package model.npcs.robots;

import model.characters.general.TarsCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.RandomSpeechBehavior;


public class TARSNPC extends RobotNPC {

	public TARSNPC(Room r) {
		super(new TarsCharacter(), 
				new MeanderingMovement(0.5), 
				new RandomSpeechBehavior("resources/TARS.TXT"), r);
	}



}

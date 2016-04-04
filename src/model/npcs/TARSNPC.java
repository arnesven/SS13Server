package model.npcs;

import model.characters.TarsCharacter;
import model.map.Room;


public class TARSNPC extends RobotNPC {

	public TARSNPC(Room r) {
		super(new TarsCharacter(), 
				new MeanderingMovement(0.5), 
				new RandomSpeechBehavior("resources/TARS.TXT"), r);
	}



}

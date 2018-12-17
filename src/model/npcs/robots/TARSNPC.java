package model.npcs.robots;

import model.GameData;
import model.characters.general.TarsCharacter;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.RandomSpeechBehavior;
import util.MyRandom;


public class TARSNPC extends RobotNPC {

	public TARSNPC(Room r) {
		super(new TarsCharacter(), 
				new MeanderingMovement(0.5), 
				new RandomSpeechBehavior("resources/TARS.TXT"), r);
	}


    public static void addATarsToRandomRoom(GameData gameData) {
        Room TARSRoom;
        do {
            TARSRoom = MyRandom.sample(gameData.getRooms());
        } while (TARSRoom instanceof NukieShipRoom);

        NPC tars = new TARSNPC(TARSRoom);
        gameData.addNPC(tars);
    }
}

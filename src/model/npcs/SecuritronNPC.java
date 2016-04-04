package model.npcs;


import model.GameData;
import model.characters.SecuritronCharacter;
import model.map.Room;

public class SecuritronNPC extends RobotNPC {

	private GameData gameData;

	public SecuritronNPC(Room r, GameData gameData) {
		super(new SecuritronCharacter(r.getID()), 
				new FollowCriminalBehavior(gameData), 
				new ArrestCriminalBehavior(gameData), r);
		this.gameData = gameData;
		this.setMaxHealth(2.0);
		this.setHealth(2.0);
	}

	

}

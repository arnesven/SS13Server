package model.npcs;


import model.GameData;
import model.characters.general.SecuritronCharacter;
import model.map.Room;
import model.npcs.behaviors.ArrestCriminalBehavior;
import model.npcs.behaviors.FollowCriminalBehavior;

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

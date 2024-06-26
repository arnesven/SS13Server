package model.npcs.robots;


import model.GameData;
import model.characters.general.SecuritronCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.behaviors.ArrestCriminalBehavior;
import model.npcs.behaviors.FollowCriminalBehavior;
import model.objects.consoles.CrimeRecordsConsole;

public class SecuritronNPC extends RobotNPC {

	private GameData gameData;


	public SecuritronNPC(Room r, GameData gameData, CrimeRecordsConsole console, SecuritronCharacter chara) throws NoSuchThingException {
		super(chara,
				new FollowCriminalBehavior(console),
				new ArrestCriminalBehavior(console), r);
		this.gameData = gameData;
		this.setMaxHealth(2.0);
		this.setHealth(2.0);
	}

	public SecuritronNPC(Room r, GameData gameData, CrimeRecordsConsole console) throws NoSuchThingException {
		this(r, gameData, console, new SecuritronCharacter(r.getID()));
	}






}

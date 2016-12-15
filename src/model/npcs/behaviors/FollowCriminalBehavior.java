package model.npcs.behaviors;

import model.Actor;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.objects.consoles.CrimeRecordsConsole;
import util.Logger;

public class FollowCriminalBehavior implements MovementBehavior {

	private CrimeRecordsConsole console;

	public FollowCriminalBehavior(CrimeRecordsConsole console) throws NoSuchThingException {
		this.console = console;
		
	}

	@Override
	public void move(NPC npc) {
		Actor mostWanted = console.getMostWanted();
		if (mostWanted == null) {
			Logger.log("SecuriTRON: no bad guys right now, guess I'll go back to sleep.");
			return;
		}
		
		Logger.log(Logger.INTERESTING, "SecuriTRON: Most wanted criminal is " + mostWanted.getBaseName() +", you're going down!");
		
		Room closerRoom = PathFinding.findCloserRoom(npc, mostWanted.getPosition());
		if (npc.getPosition() != closerRoom) {
			npc.moveIntoRoom(closerRoom);
		}
		if (closerRoom == mostWanted.getPosition()) {
			for (Actor a : closerRoom.getActors()) {
				if (a != npc) {
					a.addTolastTurnInfo(npc.getPublicName() + "; " + mostWanted.getBaseName() + " You're going down!");
				}
			}
		}
	}
}

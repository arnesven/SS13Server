package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.Room;
import model.npcs.NPC;
import model.objects.consoles.CrimeRecordsConsole;

public class FollowCriminalBehavior extends PathFinding {

	private CrimeRecordsConsole console;

	public FollowCriminalBehavior(GameData gameData) {
		console = CrimeRecordsConsole.find(gameData);
		
	}

	@Override
	public void move(NPC npc) {
		Actor mostWanted = console.getMostWanted();
		if (mostWanted == null) {
			System.out.println("SecuriTRON: no bad guys right now, guess I'll go back to sleep.");
			return;
		}
		
		System.out.println("SecuriTRON: Most wanted criminal is " + mostWanted.getBaseName() +", you're going down!");
		
		Room closerRoom = findCloserRoom(npc, mostWanted.getPosition());
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

package model.npcs.behaviors;

import model.Actor;
import model.events.ambient.LoudSoundInRoomEvent;
import model.events.ambient.SoundInRoomEvent;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.objects.consoles.CrimeRecordsConsole;
import sounds.Sound;
import util.Logger;

public class FollowCriminalBehavior implements MovementBehavior {

	private final MeanderingMovement inner;
	private CrimeRecordsConsole console;

	public FollowCriminalBehavior(CrimeRecordsConsole console) throws NoSuchThingException {
		this.console = console;
		inner = new MeanderingMovement(1.0);
	}

	@Override
	public void move(NPC npc) {
		boolean sirenOn = true;
		Actor mostWanted = console.getMostWanted(true);
		if (mostWanted == null) {
			Logger.log("SecuriTRON: no LOS to bad guys...");
			if (console.getMostWanted(false) != null) {
				Logger.log("SecuriTRON: " + console.getMostWanted(false).getBaseName() + " is hiding somewhere.... patrolling");
				inner.move(npc);
			} else {
				Logger.log("SecuriTRON: no baddies, going back to sleep");
				sirenOn = false;
			}

		} else {
			Logger.log(Logger.INTERESTING, "SecuriTRON: Most wanted criminal is " + mostWanted.getBaseName() + ", you're going down!");

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

		if (sirenOn) {
			npc.getPosition().addToEventsHappened(new LoudSoundInRoomEvent() {
				@Override
				public Sound getRealSound() {
					return new Sound("weeoo1");
				}
			});
		}
	}
}

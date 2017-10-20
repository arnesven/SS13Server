package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.HissAction;
import model.actions.characteractions.MeowingAction;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;

public class MeowOrHissBehavior extends SpontaneousAct {

	public MeowOrHissBehavior(double d) {
		super(d, new MeowingAction());
	}

	@Override
	protected void doTheAction(GameData gameData, Actor npc) {
		if (infectedOrParasiteInRoom(npc.getPosition())) {
			(new HissAction()).doTheAction(gameData, npc);
		} else {
			super.doTheAction(gameData, npc);
		}
	}

	private boolean infectedOrParasiteInRoom(Room position) {
		for (Player p : position.getClients()) {
			if (p.isInfected()) {
				return true;
			}
		}
		for (NPC npc : position.getNPCs()) {
			if (npc.isInfected() || npc instanceof ParasiteNPC) {
				return true;
			}
		}
		return false;
	}
}

package model.npcs;

import model.GameData;
import model.Player;
import model.actions.HissAction;
import model.actions.MeowingAction;
import model.map.Room;

public class MeowOrHissBehavior extends SpontaneousAct {

	public MeowOrHissBehavior(double d) {
		super(d, new MeowingAction());
	}

	@Override
	protected void doTheAction(GameData gameData, NPC npc) {
		if (infectedOrParasiteInRoom(npc.getPosition())) {
			(new HissAction()).printAndExecute(gameData, npc);
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

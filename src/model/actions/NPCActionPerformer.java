package model.actions;

import model.Client;
import model.map.Room;
import model.npcs.NPC;

public class NPCActionPerformer implements ActionPerformer {

	private NPC npc;

	public NPCActionPerformer(NPC npc) {
		this.npc = npc;
	}
	
	@Override
	public Room getPosition() {
		return npc.getPosition();
	}

	@Override
	public void addTolastTurnInfo(String string) {
		// Nothing to do here...
	}

	@Override
	public boolean isInfected() {
		return npc.isInfected();
	}

	@Override
	public String getPublicName() {
		return npc.getName();
	}

	@Override
	public boolean isClient(Client cl) {
		return false;
	}

	@Override
	public boolean isNPC(NPC npc) {
		return this.npc == npc;
	}


	@Override
	public Target getAsTarget() {
		return npc;
	}

}

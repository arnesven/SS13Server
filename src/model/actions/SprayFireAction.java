package model.actions;

import java.util.Iterator;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.items.Flamer;
import model.items.GameItem;
import model.npcs.NPC;

public class SprayFireAction extends Action {

	public SprayFireAction() {
		super("Spray Fire", false);
	}

	@Override
	protected String getVerb() {
		return "Sprayed fire";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		for (Player cl : performingClient.getPosition().getClients()) {
			if (cl != performingClient) {
				cl.beAttackedBy(performingClient, new Flamer());
			}
		}
		for (NPC npc : performingClient.getPosition().getNPCs()) {
			npc.beAttackedBy(performingClient, new Flamer());
		}
		
		Iterator<GameItem> it = performingClient.getItems().iterator();
		while (it.hasNext()) {
			if (it.next().getName().equals("Chemicals")) {
				it.remove();
				break;
			}
		}
	}

	@Override
	public void setArguments(List<String> args) {
		// TODO Auto-generated method stub

	}

}

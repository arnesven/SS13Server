package model.actions.itemactions;

import java.util.Iterator;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.actions.Action;
import model.items.general.GameItem;
import model.items.weapons.Flamer;
import model.npcs.NPC;
import model.items.general.Chemicals;

public class SprayFireAction extends Action {

	public SprayFireAction() {
		super("Spray Fire", Flamer.SENSED_AS);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Sprayed fire";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (GameItem.hasAnItem(performingClient, new Flamer()) && Chemicals.hasNChemicals(performingClient, 1)) {
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
				if (it.next() instanceof Chemicals){
					it.remove();
					break;
				}
			}
		} else if (!GameItem.hasAnItem(performingClient, new Flamer())) {
			performingClient.addTolastTurnInfo("What? The flamer is missing! Your action failed.");
		} else {
			performingClient.addTolastTurnInfo("What? Chemicals are missing! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { 	}

	@Override
	public String getDistantDescription(Actor whosAsking) {
		return "Something is burning...";
	}
	
}

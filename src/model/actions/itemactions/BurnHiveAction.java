package model.actions.itemactions;

import java.util.Iterator;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.damage.FireDamage;
import model.items.general.GameItem;
import model.items.weapons.Flamer;
import model.objects.general.HiveObject;
import model.items.general.Chemicals;

public class BurnHiveAction extends Action {

	private HiveObject hive;

	public BurnHiveAction(HiveObject ob) {
		super("Incinerate Hive", Flamer.SENSED_AS);
		this.hive = ob;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Incinerated the hive";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (GameItem.hasAnItem(performingClient, new Flamer()) && Chemicals.hasNChemicals(performingClient,
                Flamer.CHEMS_NEEDED_TO_BURN_HIVE)) {
			for (int i = Flamer.CHEMS_NEEDED_TO_BURN_HIVE; i > 0; --i) {
				Iterator<GameItem> it = performingClient.getItems().iterator();
				while (it.hasNext()) {
					if (it.next() instanceof Chemicals && ((Chemicals) it.next()).isFlammable()) {
						it.remove();
						break;
					}
				}

			}

			hive.beExposedTo(performingClient, new FireDamage(3.0));
            hive.setBreaker(performingClient);
			performingClient.addTolastTurnInfo("You destroyed the hive with the flamer!");
		} else if (!GameItem.hasAnItem(performingClient, new Flamer())) {
			performingClient.addTolastTurnInfo("What? The flamer is gone. Your action failed.");
		} else {
			performingClient.addTolastTurnInfo("What? Some chemicals are missing. Your action failed.");
		}
	}

	
	

	@Override
	public void setArguments(List<String> args, Actor p) { }
	
	@Override
	public String getDistantDescription(Actor whosAsking) {
		return "Something is burning...";
	}

}

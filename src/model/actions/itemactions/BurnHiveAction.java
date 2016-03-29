package model.actions.itemactions;

import java.util.Iterator;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;
import model.items.weapons.Flamer;
import model.objects.HiveObject;
import model.items.Chemicals;

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
		if (GameItem.hasAnItem(performingClient, new Flamer()) && Chemicals.hasNChemicals(performingClient, 3)) {	
			for (int i = 3; i > 0; --i) {
				Iterator<GameItem> it = performingClient.getItems().iterator();
				while (it.hasNext()) {
					if (it.next() instanceof Chemicals) {
						it.remove();
						break;
					}
				}

			}

			hive.setHealth(0.0);
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

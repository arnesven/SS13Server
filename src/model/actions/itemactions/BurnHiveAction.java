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

public class BurnHiveAction extends Action {

	private HiveObject hive;

	public BurnHiveAction(HiveObject ob) {
		super("Incinerate Hive", Flamer.SENSED_AS);
		this.hive = ob;
	}

	@Override
	protected String getVerb() {
		return "Incinerated the hive";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		hive.setHealth(0.0);
		performingClient.addTolastTurnInfo("You destroyed the hive with the flamer!");
		
		for (int i = 3; i > 0; --i) {
			Iterator<GameItem> it = performingClient.getItems().iterator();
			while (it.hasNext()) {
				if (it.next().getName().equals("Chemicals")) {
					it.remove();
					break;
				}
			}
			
		}
	}

	@Override
	public void setArguments(List<String> args) {

	}
	
	@Override
	public String getDistantDescription() {
		return "Something is burning...";
	}

}

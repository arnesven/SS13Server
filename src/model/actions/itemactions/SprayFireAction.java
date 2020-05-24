package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.weapons.Flamer;
import model.objects.general.BreakableObject;

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
		if (GameItem.hasAnItem(performingClient, new Flamer())) {
            for (Actor a : performingClient.getPosition().getActors()) {
				if (a != performingClient) {
                    if (!a.isDead()) {
                        (new Flamer()).doAttack(performingClient, a.getAsTarget(), gameData);
                    }
				}
			}
			for (Object o : performingClient.getPosition().getObjects()) {
                if (o instanceof BreakableObject) {
                    (new Flamer()).doAttack(performingClient, ((BreakableObject)o), gameData);
                }
			}
		} else if (!GameItem.hasAnItem(performingClient, new Flamer())) {
			performingClient.addTolastTurnInfo("What? The flamer is missing! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { 	}

	@Override
	public String getDistantDescription(Actor whosAsking) {
		return "Something is burning...";
	}
	
}

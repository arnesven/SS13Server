package model.items.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

public class RemoteBomb extends BombItem {

	private boolean remoteGotten = false;
	
	public RemoteBomb() {
		super("Remote Bomb", 750);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
		super.addYourActions(gameData, at, cl);
		if (!remoteGotten) {
			at.add(new Action("Separate Bomb Detonator",
					SensoryLevel.OPERATE_DEVICE) {
				
				@Override
				protected String getVerb(Actor whosAsking) {
					return getOperationString();
				}
				
				@Override
				public void setArguments(List<String> args, Actor p) { }
				
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (hasAnItem(performingClient, new RemoteBomb())) {
					remoteGotten = true;
					GameItem det = new BombDetonator(RemoteBomb.this);
					performingClient.addItem(det, null);
					performingClient.addTolastTurnInfo("You separated the detonator from the bomb.");
					RemoteBomb.this.setWeight(RemoteBomb.this.getWeight() - det.getWeight());
					} else {
						performingClient.addTolastTurnInfo("What? the bomb was gone! Your action failed.");
					}
				}
			});
			
		}
		
	}

	@Override
	public RemoteBomb clone() {
		return new RemoteBomb();
	}


}

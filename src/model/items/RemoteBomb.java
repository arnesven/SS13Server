package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;

public class RemoteBomb extends BombItem {

	private boolean remoteGotten = false;
	
	public RemoteBomb() {
		super("Remote Bomb");
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (!remoteGotten) {
			at.add(new Action("Separate Bomb Detonator",
					SensoryLevel.OPERATE_DEVICE) {
				
				@Override
				protected String getVerb(Actor whosAsking) {
					return BombItem.getOperationString();
				}
				
				@Override
				public void setArguments(List<String> args, Actor p) { }
				
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					remoteGotten = true;
					GameItem det = new BombDetonator(RemoteBomb.this);
					performingClient.addItem(det, null);
					performingClient.addTolastTurnInfo("You separated the detonator from the bomb.");
					RemoteBomb.this.setWeight(RemoteBomb.this.getWeight() - det.getWeight());
				}
			});
			
		}
		
	}


}

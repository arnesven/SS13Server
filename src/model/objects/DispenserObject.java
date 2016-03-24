package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.GameItem;

public abstract class DispenserObject extends ElectricalMachinery {

	private List<GameItem> inventory = new ArrayList<>();
	
	public DispenserObject(String name) {
		super(name);
	}
	
	public void addItem(GameItem it) {
		inventory.add(it);
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new Action(DispenserObject.this.getName(),
				          SensoryLevel.OPERATE_DEVICE) {
			
			@Override
			public void setArguments(List<String> args, Actor p) { }
			
			@Override
			protected String getVerb(Actor whosAsking) {
				return "used " + DispenserObject.this.getName();
			}
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				if (inventory.size() > 0) {
					GameItem it = inventory.remove(0);
					performingClient.addItem(it, DispenserObject.this);
					performingClient.addTolastTurnInfo("You got " + it.getPublicName(performingClient) + ".");
				} else {
					performingClient.addTolastTurnInfo("The dispenser is empty.");
				}
				
			}
		});
	}

	protected abstract GameItem dispensedItem();

}

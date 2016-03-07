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

	private int itemsLeft;

	public DispenserObject(String name, int i) {
		super(name);
		this.itemsLeft = i;
	}
	
	@Override
	public void addActions(Player cl, ArrayList<Action> at) {
		at.add(new Action(DispenserObject.this.getName(),
				          SensoryLevel.OPERATE_DEVICE) {
			
			@Override
			public void setArguments(List<String> args) { }
			
			@Override
			protected String getVerb() {
				return "used " + DispenserObject.this.getName();
			}
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				if (itemsLeft > 0) {
					GameItem it = DispenserObject.this.dispensedItem();
					performingClient.addItem(it);
					itemsLeft--;
					performingClient.addTolastTurnInfo("You got " + it.getName() + ".");
				} else {
					performingClient.addTolastTurnInfo("The dispenser is empty.");
				}
				
			}
		});
	}

	protected abstract GameItem dispensedItem();

}

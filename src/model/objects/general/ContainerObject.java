package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.map.rooms.Room;

public class ContainerObject extends GameObject {

private List<GameItem> inventory = new ArrayList<>();
	
	public ContainerObject(String name, Room position) {
		super(name, position);
	}
	
	public List<GameItem> getInventory() {
		return inventory;
	}
	
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Actor cl,
                                      ArrayList<Action> at) {
		super.addSpecificActionsFor(gameData, cl, at);
		
		if (inventory.size() > 0) {
			at.add(new Action("Retrieve from " + ContainerObject.this.getPublicName(cl), SensoryLevel.OPERATE_DEVICE) {
				
				private GameItem selectedItem;
				
				@Override
				protected String getVerb(Actor whosAsking) {
					return "retrieved something from the " + 
							ContainerObject.this.getPublicName(whosAsking).toLowerCase() + ".";
				}

				@Override
				public ActionOption getOptions(GameData gameData, Actor whosAsking) {
					ActionOption opt = super.getOptions(gameData, whosAsking);
					for (GameItem it : inventory) {
						opt.addOption(it.getPublicName(whosAsking));
					}
					return opt;
				}
				
				@Override
				public void setArguments(List<String> args, Actor performingClient) {
					for (GameItem it : inventory) {
						if (args.get(0).equals(it.getPublicName(performingClient))) {
							selectedItem = it;
							return;
						}
					}
					
					selectedItem = null;
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (selectedItem == null) {
						performingClient.addTolastTurnInfo(Action.FAILED_STRING);
					} else {
						performingClient.getCharacter().giveItem(selectedItem, null);
						inventory.remove(selectedItem);
						performingClient.addTolastTurnInfo("You retrieved the " + 
										selectedItem.getPublicName(performingClient));
					}
				}
			});
		}
	}


    public boolean accessibleTo(Actor ap) {
        return true;
    }
}

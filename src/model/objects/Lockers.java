package model.objects;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.ActionOption;
import model.items.GameItem;
import model.items.MedKit;
import model.items.suits.OutFit;
import model.map.Room;
import model.modes.GameMode;
import model.actions.SensoryLevel;

public class Lockers extends GameObject {

	private List<GameItem> inventory = new ArrayList<>();
	
	public Lockers(Room position) {
		super("Lockers", position);
		inventory.add(new MedKit());
		
		//add some random outfits.
		do {
			inventory.add(new OutFit(MyRandom.sample(GameMode.getAllCrewAsStrings())));
		} while (MyRandom.nextDouble() < 0.33);
	}
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Player cl,
			ArrayList<Action> at) {
		super.addSpecificActionsFor(gameData, cl, at);
		
		if (inventory.size() > 0) {
			at.add(new Action("Retrieve from Lockers", SensoryLevel.OPERATE_DEVICE) {
				
				private GameItem selectedItem;
				
				@Override
				protected String getVerb(Actor whosAsking) {
					return "retrieved something from the lockers";
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
	

}

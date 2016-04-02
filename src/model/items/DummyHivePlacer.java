package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.objects.DummyHive;


public class DummyHivePlacer extends GameItem {

	private boolean placeAsFound = true;
	
	public DummyHivePlacer() {
		super("Dummy Hive", 1.0);
	}
	

	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		at.add(new Action("Place Dummy Hive", SensoryLevel.PHYSICAL_ACTIVITY) {
			
			@Override
			protected String getVerb(Actor performingClient) {
				return "placed a dummy hive";
			}
			
			@Override
			public ActionOption getOptions(GameData gameData, Actor whosAsking) {
				ActionOption opt = new ActionOption("Place Dummy Hive");
				opt.addOption("As Found");
				opt.addOption("As Hidden");
				return opt;
			}
			
			@Override
			public void setArguments(List<String> args, Actor p) {
				if (args.get(0).equals("As Found")) {
					placeAsFound = true;
				} else {
					placeAsFound = false;
				}
			}
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				if (GameItem.hasAnItem(performingClient, new DummyHivePlacer())) {
					performingClient.getItems().remove(DummyHivePlacer.this);
					performingClient.getPosition().addObject(new DummyHive(placeAsFound, 
							performingClient.getPosition()));
				} else {
					performingClient.addTolastTurnInfo("What? the dummy hive is gone! Your action failed.");
				}
				
			}
			
		});
	}



	@Override
	public DummyHivePlacer clone() {
		return new DummyHivePlacer();
	}

	@Override
	protected char getIcon() {
		return 'H';
	}
	
}

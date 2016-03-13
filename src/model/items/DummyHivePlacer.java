package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
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
			protected String getVerb() {
				return "placed a dummy hive";
			}
			
			@Override
			public String toString() {
				return "Place Dummy Hive{As Found{}As Hidden{}}";
			}
			
			@Override
			public void setArguments(List<String> args) {
				if (args.get(0).equals("As Found")) {
					placeAsFound = true;
				} else {
					placeAsFound = false;
				}
			}
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				performingClient.getItems().remove(DummyHivePlacer.this);
				performingClient.getPosition().addObject(new DummyHive(placeAsFound));
				
			}
			
		});
	}

}

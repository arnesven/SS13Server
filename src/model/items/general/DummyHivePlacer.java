package model.items.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.general.DummyHive;


public class DummyHivePlacer extends GameItem {

	private boolean placeAsFound = true;
	
	public DummyHivePlacer() {
		super("Dummy Hive", 1.0, 380);
	}
	

	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
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
                placeAsFound = args.get(0).equals("As Found");
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
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("keycard", "card.png", 1);
    }
}

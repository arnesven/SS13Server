package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.GameMap;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;
import model.objects.general.AirlockPanel;
import util.Logger;

public class AirlockOverrideAction extends ConsoleAction {

	private List<AirLockRoom> knownAirlocks = new ArrayList<>();
	private AirLockRoom selected;
	
	
	public AirlockOverrideAction(GameData gameData) {
		super("Airlock Override", SensoryLevel.OPERATE_DEVICE);
     	for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
			if (r instanceof AirLockRoom) {
				knownAirlocks.add((AirLockRoom) r);
			}
		}
	}


	@Override
	public void setArguments(List<String> args, Actor p) {
	    Logger.log("Setting args: " + args.toString());
		for (int i = 0 ; i < knownAirlocks.size() ; ++i) {
			if (args.get(0).contains(knownAirlocks.get(i).getName())) {
				selected = knownAirlocks.get(i);
			}
		}
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption res = new ActionOption("Airlock Override");
		for (int i = 0; i < knownAirlocks.size() ; ++i) {
			if (knownAirlocks.get(i).hasPressure()) {
				res.addOption("Depressurize " + knownAirlocks.get(i).getName());
			} else {
				res.addOption("Pressurize " + knownAirlocks.get(i).getName());
			}
		}
		return res;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (selected.hasPressure()) {
			selected.depressurize(gameData, performingClient);
		} else {
			selected.pressurize(gameData, performingClient);
		}
	}

    @Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with the Airlock Override";
	}

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }

	public List<AirLockRoom> getKnownAirlocks() {
		return knownAirlocks;
	}
}

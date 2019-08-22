package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.AirlockPanel;
import util.Logger;

public class AirlockOverrideAction extends ConsoleAction {

	private List<Room> rooms = new ArrayList<>();
	private List<AirlockPanel> panels = new ArrayList<>();
	private AirlockPanel selected;
	
	
	public AirlockOverrideAction(GameData gameData) {
		super("Airlock Override", SensoryLevel.OPERATE_DEVICE);
        Logger.log("Created airlock override action");
		for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
			for (GameObject ob : r.getObjects()) {
				if (ob instanceof AirlockPanel) {
					rooms.add(r);
					panels.add((AirlockPanel)ob);
                    Logger.log("   found an airlock panel in" + r.getName());
				}
			}
		}
	}


	@Override
	public void setArguments(List<String> args, Actor p) {
	    Logger.log("Setting args: " + args.toString());
		for (int i = 0 ; i < rooms.size() ; ++i) {
			if (args.get(0).contains(rooms.get(i).getName())) {
				selected = panels.get(i);
			}
		}
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption res = new ActionOption("Airlock Override");
		for (int i = 0; i < rooms.size() ; ++i) {
			if (panels.get(i).getPressure()) {
				res.addOption("Depressurize " + rooms.get(i).getName());
			} else {
				res.addOption("Pressurize " + rooms.get(i).getName());
			}
		}
		return res;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		selected.makeApplicableAction(gameData).doTheAction(gameData, performingClient);
	}

    @Override
    public void setOverlayArguments(List<String> args, Actor performingClient) {
        super.setOverlayArguments(args.subList(1, args.size()), performingClient);
    }

    @Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddeled with the Airlock Override";
	}

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }
}

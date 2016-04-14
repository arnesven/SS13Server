package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.map.Room;
import model.objects.general.GameObject;
import model.objects.general.PressurePanel;

public class AirlockOverrideAction extends Action {

	private List<Room> rooms = new ArrayList<>();
	private List<PressurePanel> panels = new ArrayList<>();
	private PressurePanel selected;
	
	
	public AirlockOverrideAction(GameData gameData) {
		super("Airlock Override", SensoryLevel.OPERATE_DEVICE);
		for (Room r : gameData.getRooms()) {
			for (GameObject ob : r.getObjects()) {
				if (ob instanceof PressurePanel) {
					rooms.add(r);
					panels.add((PressurePanel)ob);
				}
			}
		}
	}


	@Override
	public void setArguments(List<String> args, Actor p) {
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
	protected String getVerb(Actor whosAsking) {
		return "Fiddeled with the Airlock Override";
	}

}

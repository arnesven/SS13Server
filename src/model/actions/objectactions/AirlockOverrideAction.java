package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.map.Room;
import model.objects.GameObject;
import model.objects.PressurePanel;

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
	public void setArguments(List<String> args) {
		for (int i = 0 ; i < rooms.size() ; ++i) {
			if (args.get(0).contains(rooms.get(i).getName())) {
				selected = panels.get(i);
			}
		}
		
	}
	
	@Override
	public String toString() {
		String res = "Airlock Override{";
		for (int i = 0; i < rooms.size() ; ++i) {
			if (panels.get(i).getPressure()) {
				res += "Depressurize " + rooms.get(i).getName() + "{}";
			} else {
				res += "Pressurize " + rooms.get(i).getName() + "{}";
			}
		}
		res += "}";
		return res;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		selected.makeApplicableAction(gameData).doTheAction(gameData, performingClient);
	}

}

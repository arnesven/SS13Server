package model.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel.VisualLevel;
import model.map.GameMap;
import model.map.MapBuilder;
import model.map.Room;

public class SecurityConsoleAction extends Action {

	private String chosen;

	public SecurityConsoleAction() {
		super("Security Console", SensoryLevel.OPERATE_DEVICE);
	}

	@Override
	protected String getVerb() {
		return "checked the security cams";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		gameData.executeAtEndOfRound(performingClient, this);
	}
	
	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		
		performingClient.addTolastTurnInfo("Security Camera;");
		Room viewdRoom = gameData.getRoom(chosen);
		StringBuffer occupants = new StringBuffer("-->In the room; ");
		for (Actor a : viewdRoom.getActors()) {
			occupants.append(a.getPublicName() + ",");
		}
		performingClient.addTolastTurnInfo(occupants.toString());
		
		for (Action a : viewdRoom.getActionsHappened()) {
			if (a.getSense().visual == VisualLevel.CLEARLY_VISIBLE) {
				performingClient.addTolastTurnInfo("-->" + a.getDescription());
			}
		}
	}
	
	@Override
	public String toString() {
		GameMap gm = MapBuilder.createMap();
		StringBuffer rooms = new StringBuffer();
		List<String> names = new ArrayList<>();
		for (Room r : gm.getRooms()) {
			names.add(r.getName());
		}
		Collections.sort(names);
		for (String s : names) {
			rooms.append(s + "{}");
		}
		return getName() + "{"+ rooms.toString() + "}";
	}

	@Override
	public void setArguments(List<String> args) {	
		chosen = args.get(0);
	}

}

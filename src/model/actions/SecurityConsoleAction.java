package model.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel.VisualLevel;
import model.events.Event;
import model.map.GameMap;
import model.map.MapBuilder;
import model.map.Room;
import model.objects.SecurityCameraConsole;

public class SecurityConsoleAction extends Action {

	private String chosen;
	private SecurityCameraConsole console;

	public SecurityConsoleAction(SecurityCameraConsole securityCameraConsole) {
		super("Security Console", SensoryLevel.OPERATE_DEVICE);
		this.console = securityCameraConsole;
	}

	@Override
	protected String getVerb() {
		return "checked the security cams";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (!console.isInUse()) {
			console.setChosen(chosen);
		}
		console.setInUse(true);
		gameData.executeAtEndOfRound(performingClient, this);
	}
	
	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		
		performingClient.addTolastTurnInfo("Security Camera in " + console.getChosen() + ";");
		Room viewdRoom = gameData.getRoom(console.getChosen());
		StringBuffer occupants = new StringBuffer("-->In the room; ");
		for (Event e : viewdRoom.getEvents()) {
			if (e.getSense().visual == VisualLevel.CLEARLY_VISIBLE) {
				performingClient.addTolastTurnInfo("-->"+e.howYouAppear(performingClient));
			}
		}
		String nobody = "Nobody";
		for (Actor a : viewdRoom.getActors()) {
			occupants.append(a.getPublicName() + ",");
			nobody = "";
		}
		occupants.append(nobody);
		performingClient.addTolastTurnInfo(occupants.toString());
		
		
		
		for (Action a : viewdRoom.getActionsHappened()) {
			if (a.getSense().visual == VisualLevel.CLEARLY_VISIBLE) {
				performingClient.addTolastTurnInfo("-->" + a.getDescription());
			}
		}
		console.setInUse(false);
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

package model.actions.objectactions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.characters.decorators.SeeRoomOverlayDecorator;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.consoles.SecurityCameraConsole;

public class SecurityConsoleAction extends ConsoleAction {

	private String chosen;
	private SecurityCameraConsole console;

	public SecurityConsoleAction(SecurityCameraConsole securityCameraConsole) {
		super("Security Cameras", SensoryLevel.OPERATE_DEVICE);
		this.console = securityCameraConsole;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
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
		try {
            performingClient.addTolastTurnInfo("Security Camera in " + console.getChosen() + ";");
            Room viewdRoom = gameData.getRoom(console.getChosen());
            StringBuffer occupants = new StringBuffer("-->In the room; ");
            for (Event e : viewdRoom.getEvents()) {
                if (e.getSense().visual == VisualLevel.CLEARLY_VISIBLE) {
                    performingClient.addTolastTurnInfo("-->" + e.howYouAppear(performingClient));
                }
            }
            String nobody = "Nobody";
            for (Actor a : viewdRoom.getActors()) {
                occupants.append(a.getPublicName() + ",");
                nobody = "";
            }
            occupants.append(nobody);
            performingClient.addTolastTurnInfo(occupants.toString());

            performingClient.setCharacter(new SeeRoomOverlayDecorator(performingClient.getCharacter(), gameData, viewdRoom));

            for (Action a : viewdRoom.getActionsHappened()) {
                if (a.getSense().visual == VisualLevel.CLEARLY_VISIBLE) {
                    performingClient.addTolastTurnInfo("-->" + a.getDescription(performingClient));
                }
            }
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
		console.setInUse(false);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption(this.getName());
		//GameMap gm = MapBuilder.createMap();
		List<String> names = new ArrayList<>();
		for (Room r : console.getConnectedCameraRooms(gameData)) {
			names.add(r.getName());
		}
		Collections.sort(names);
		for (String s : names) {
			opt.addOption(s);
		}
		return opt;
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		chosen = args.get(0);
	}

}

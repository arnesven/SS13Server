package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.modes.ChangelingGameMode;
import model.modes.HostGameMode;
import model.modes.OperativesGameMode;
import model.modes.TraitorGameMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class ShipsLogsConsole extends Console {
    public ShipsLogsConsole(Room derelictBridge) {
        super("Ship's Log", derelictBridge);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new ShipsLogsAction());
    }

    private class ShipsLogsAction extends Action {

        public ShipsLogsAction() {
            super("Read Ship's Logs", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "checked the ship's log";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (gameData.getGameMode() instanceof TraitorGameMode) {
                performingClient.addTolastTurnInfo("\"... the station is being utterly destroyed by disgruntled crew members.\"");
            } else if (gameData.getGameMode() instanceof HostGameMode) {
                performingClient.addTolastTurnInfo("\"... some strange infection has spread throughout the station, making the crew go insane\".");
            } else if (gameData.getGameMode() instanceof OperativesGameMode) {
                performingClient.addTolastTurnInfo("\"... the station is being assaulted by men in red uniforms. They seem to be after the nuclear activation codes.\"");
            } else if (gameData.getGameMode() instanceof ChangelingGameMode) {
                performingClient.addTolastTurnInfo("\"... some horrible shape-shifting creature is running amok on the station.\"");
            } else {
                performingClient.addTolastTurnInfo("\"... catastrophes are breaking out left and right. Save yourselves!\"");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

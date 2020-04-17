package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.objects.consoles.TeleportConsole;

import java.util.Collection;
import java.util.List;

/**
 * Created by erini02 on 15/09/17.
 */
public class ScanSpaceAction extends Action {
    private final TeleportConsole console;

    public ScanSpaceAction(TeleportConsole console) {
        super("Scan Space", SensoryLevel.OPERATE_DEVICE);
        this.console = console;

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Teletronics";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        console.doSpaceScan(gameData);
        for (String info : console.getScannedData()) {
            performingClient.addTolastTurnInfo(info);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}

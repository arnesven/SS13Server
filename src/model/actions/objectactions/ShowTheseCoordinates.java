package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.TeleportConsole;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 27/11/16.
 */
public class ShowTheseCoordinates extends Action {
    private final TeleportConsole teleporter;


    public ShowTheseCoordinates(TeleportConsole teleportConsole, GameData gameData) {
        super("Local Coordinates", SensoryLevel.OPERATE_DEVICE);
        this.teleporter = teleportConsole;
        Integer[] coordinates = teleportConsole.getLocalCoordinates(gameData);
        setName("Local Coordinates = (" + (coordinates[0]) + "-" + (coordinates[1]) + "-" + (coordinates[2]) + ")");

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with teleport console";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo(this.getName());
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}

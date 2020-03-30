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
    private static final int xoff = MyRandom.nextInt(100);
    private static final int yoff = MyRandom.nextInt(100);
    private static final int zoff = MyRandom.nextInt(100);
    private final TeleportConsole teleporter;


    public ShowTheseCoordinates(TeleportConsole teleportConsole, GameData gameData) {
        super("Local Coordinates", SensoryLevel.OPERATE_DEVICE);
        this.teleporter = teleportConsole;
        try {
            Integer[] coordinates = gameData.getMap().getPositionForLevel(gameData.getMap().getLevelForRoom(teleportConsole.getPosition()).getName());
            setName("Local Coordinates = (" + (coordinates[0]*100+xoff) + "-" +
                    (coordinates[1]*100+yoff) + "-" +
                    (coordinates[2]*100+zoff) + ")");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

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

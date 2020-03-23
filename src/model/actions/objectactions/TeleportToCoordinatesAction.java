package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.TeleportAction;
import model.characters.decorators.TumblingThroughSpaceDecorator;
import model.items.general.Teleporter;
import model.map.GameMap;
import model.map.rooms.Room;
import model.map.rooms.SpaceRoom;
import util.Logger;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 27/11/16.
 */
public class TeleportToCoordinatesAction extends Action {

    private Integer[] selected;

    public TeleportToCoordinatesAction() {
        super("Teleport to Coordinates", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Teletronics Console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (int x = 0; x < GameMap.MATRIX_DIM_MAX; x++) {
            for (int y = 0; y < GameMap.MATRIX_DIM_MAX; y++) {
                for (int z = 0; z < GameMap.MATRIX_DIM_MAX; z++) {
                    opts.addOption("(" + (x*100 + MyRandom.nextInt(100)) + "-" +
                                    (y*100 + MyRandom.nextInt(100)) + "-" +
                                    (z*100 + MyRandom.nextInt(100)) + ")");
                }
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Logger.log("Teleporting to " + selected[0] + "-" + selected[1] + "-" + selected[2]);
        String level = gameData.getMap().getLevelForCoordinates(selected, gameData);
        Logger.log("  which is level " + level);

        Room r = MyRandom.sample(gameData.getMap().getRoomsForLevel(level));

        Teleporter tele = new Teleporter();
        tele.setMarked(r);
        Action a = new TeleportAction(tele);
        ((TeleportAction)a).setTarget(performingClient);
        a.doTheAction(gameData, performingClient);
        if (r instanceof SpaceRoom) {
            performingClient.setCharacter(new TumblingThroughSpaceDecorator(performingClient.getCharacter()));
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        String res = args.get(0).substring(1, args.get(0).length()-1);
        String[] parts = res.split("-");
        Logger.log("res is: " + res);
        selected = new Integer[]{Integer.parseInt(parts[0])/100,
                                 Integer.parseInt(parts[1])/100,
                                 Integer.parseInt(parts[2])/100};
    }
}

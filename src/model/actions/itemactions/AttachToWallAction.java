package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.BombItem;
import model.map.Architecture;
import model.map.rooms.Room;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 24/11/16.
 */
public class AttachToWallAction extends Action {

    private final Map<Room, Point2D> arc;
    private final BombItem bomb;
    private Room selected;

    public AttachToWallAction(GameData gameData, Actor cl, BombItem bombItem) {
        super("Attach Bomb to Wall", SensoryLevel.OPERATE_DEVICE);
        Architecture arch = null;
        try {
            arch = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(cl.getPosition()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        arc = arch.getPossibleNewDoors(cl.getPosition());
        this.bomb = bombItem;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Map.Entry<Room, Point2D> pair : arc.entrySet()) {
            opts.addOption("to " + pair.getKey().getName());
        }
        return opts;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "attached a bomb to the wall";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        bomb.setAttached(selected);
        performingClient.getItems().remove(bomb);
        performingClient.getPosition().addItem(bomb);
    }


    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Map.Entry<Room, Point2D> pair : arc.entrySet()) {
            if (args.get(0).contains(pair.getKey().getName())) {
                selected = pair.getKey();
            }
        }
    }

}

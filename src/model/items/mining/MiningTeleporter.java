package model.items.mining;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.TeleportAction;
import model.items.NoSuchThingException;
import model.items.general.Teleporter;
import model.map.GameMap;
import model.map.rooms.Room;
import util.MyRandom;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningTeleporter extends Teleporter {

    public MiningTeleporter() {
        setName("Mining Teleporter");
    }


    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        ArrayList<Action> atTmp = new ArrayList<>();

        try {
            boolean toSS13 = false;
            Room r;
            if (gameData.getMap().getLevelForRoom(cl.getPosition()).equals(GameMap.STATION_LEVEL_NAME)) {
                r = gameData.getMap().getRoom("Mining Station");
            } else {
                r = MyRandom.sample(gameData.getRooms());
                toSS13 = true;
            }

            setMarked(r);
            super.addYourActions(gameData, atTmp, cl);

            if (toSS13) {
                for (Action a : atTmp) {
                    if (a instanceof TeleportAction) {
                        a.setName("Teleport to SS13");
                    }
                }
            }
            atTmp.removeIf((Action a) -> a.getName().equals("Mark Coordinates"));
            at.addAll(atTmp);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }




    }
}

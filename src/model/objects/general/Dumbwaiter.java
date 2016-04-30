package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.DumbwaiterAction;
import model.actions.objectactions.MakeBombAction;
import model.items.NoSuchThingException;
import model.items.general.*;
import model.map.KitchenRoom;
import model.map.Room;
import util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by erini02 on 28/04/16.
 */
public class Dumbwaiter extends GameObject {




    public Dumbwaiter(KitchenRoom kitchenRoom) {
        super("Dumbwaiter", kitchenRoom);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        at.add(new MakeBombAction(this));
        if (cl.getItems().size() > 0) {
            at.add(new DumbwaiterAction(this));
        }
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("dumbwaiter", "storage2.png", 3, 13);
    }

    public static Dumbwaiter find(GameData gameData) throws NoSuchThingException {
        for (GameObject ob : gameData.getObjects()) {
            if (ob instanceof Dumbwaiter) {
                return (Dumbwaiter) ob;
            }
        }
        throw new NoSuchThingException("No dumbwaiter found on station");
    }

    public List<Room> getDestinations(GameData gameData) {
        String[] places = new String[]{"Greenhouse", "Bar", "Dorms", "Office", "Captain's Quarters", "Bridge", "Security Station" ,
                                        "Shuttle Gate", "Sickbay", "Lab", "Chapel", "Generator", "AI Core", "Kitchen"};
        List<Room> result = new ArrayList<>();
        for (String place :  places) {

            Room r = gameData.getRoom(place);
            if (r == null) {
                Logger.log(Logger.CRITICAL, "Dumbwaiter did not find room: " + place);
            } else {
                result.add(r);
            }
        }
        Collections.sort(result, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return result;
    }
}

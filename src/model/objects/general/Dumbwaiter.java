package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.DumbwaiterAction;
import model.actions.objectactions.MakeBombAction;
import model.events.NoSuchEventException;
import model.items.NoSuchThingException;
import model.map.KitchenRoom;
import model.map.Room;
import util.Logger;

import java.util.*;

/**
 * Created by erini02 on 28/04/16.
 */
public class Dumbwaiter extends GameObject {




    public Dumbwaiter(KitchenRoom kitchenRoom) {
        super("Dumbwaiter", kitchenRoom);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
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

    public List<Room> getDestinations(GameData gameData) {
        String[] places = new String[]{"Greenhouse", "Bar", "Dorms", "Office", "Captain's Quarters", "Bridge", "Security Station" ,
                                        "Shuttle Gate", "Sickbay", "Lab", "Chapel", "Generator", "AI Core", "Kitchen"};
        List<Room> result = new ArrayList<>();
        for (String place :  places) {

            try {
                Room r = gameData.getRoom(place);
                result.add(r);
            } catch (NoSuchThingException nse) {
                    Logger.log(Logger.CRITICAL, "Dumbwaiter did not find room: " + place);
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

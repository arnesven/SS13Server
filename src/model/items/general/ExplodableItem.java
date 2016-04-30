package model.items.general;

import model.Actor;
import model.GameData;
import model.items.foods.ExplodingFood;
import model.map.Room;

/**
 * Created by erini02 on 28/04/16.
 */
public interface ExplodableItem {
    GameItem getAsItem();

    void explode(GameData gameData, Room room, Actor maker);

    void setConceledWithin(ExplodingFood explodingFood);
}

package model;

import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public interface ItemHolder {
    List<GameItem> getItems();
    String getPublicName(Actor forWhom);
    Room getPosition();
}

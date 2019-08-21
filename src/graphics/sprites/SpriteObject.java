package graphics.sprites;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.List;

public interface SpriteObject {

    Sprite getSprite(Actor whosAsking);

    String getPublicName(Actor whosAsking);

    List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom);
}

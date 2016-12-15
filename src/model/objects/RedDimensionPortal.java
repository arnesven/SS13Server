package model.objects;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.DimensionPortal;

/**
 * Created by erini02 on 24/11/16.
 */
public class RedDimensionPortal extends DimensionPortal {
    public RedDimensionPortal(GameData gameData, Room otherDim, Room derelictBridge, String red) {
        super(gameData, otherDim, derelictBridge, red);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("portalred", "weapons2.png", 22, 10, 32, 32);
    }
}

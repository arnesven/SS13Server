package model.objects.general;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.events.animation.AnimatedSprite;
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
        return new AnimatedSprite("portalred", "weapons2.png",
                20, 10, 32, 32, this, 3, true);
    }
}

package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class FlashedVision extends OverlaySpriteCollector {
    @Override
    protected List<OverlaySprite> getSpritesForSpecificVision(Player whoFor, GameData gameData) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(whoFor.getSprite(whoFor));
        return getOverlaysForSpritesInRoom(gameData, sprs, whoFor.getPosition(), whoFor);
    }
}

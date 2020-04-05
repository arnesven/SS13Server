package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class OnlyTheAlwaysSpritesVision extends OverlaySpriteCollector {
    @Override
    protected List<OverlaySprite> getSpritesForSpecificVision(Player whoFor, GameData gameData) {
        return new ArrayList<>();
    }
}

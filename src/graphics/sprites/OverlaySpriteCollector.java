package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.map.doors.DoorAnimationEvent;
import model.map.rooms.Room;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class OverlaySpriteCollector {

    public List<OverlaySprite> getOverlaySprites(Player player, GameData gameData) {
        List<OverlaySprite> result = getSpritesForSpecificVision(player, gameData);
        result.addAll(getAlwaysSprites(player, gameData));
        if (result.isEmpty()) {
            return dummyList();
        }
        return result;
    }

    public static List<OverlaySprite> noVision() {
        return dummyList();
    }

    protected abstract List<OverlaySprite> getSpritesForSpecificVision(Player whoFor, GameData gameData);



    private static List<OverlaySprite> dummyList() {
        Sprite sp = Sprite.blankSprite();
        ArrayList<OverlaySprite> dummyList = new ArrayList<>();
        dummyList.add(new OverlaySprite(sp, 0, 0, 0, null, null));
        return dummyList;
    }

    private static List<OverlaySprite> getAlwaysSprites(Player player, GameData gameData) {
        List<OverlaySprite> strs = new ArrayList<>();
        for (Room r : player.getVisibleMap(gameData)) {
            List<Sprite> sprs = r.getAlwaysSprites(player);
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sprs, r, player));
        }
        return strs;
    }

    protected static List<OverlaySprite> getOverlaysForSpritesInRoom(GameData gameData, List<Sprite> sprites, Room r, Player forWhom) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        for (Sprite sp : sprites) {
            if (sp.getObjectReference() != null && sp.getObjectReference().hasAbsolutePosition()) {
                double x = sp.getObjectReference().getAbsoluteX();
                double y = sp.getObjectReference().getAbsoluteY();
                double z = sp.getObjectReference().getAbsoluteZ();
                strs.add(new OverlaySprite(sp, x, y, z,
                        r, forWhom, sp.getMaximumFrames()));
            } else {
                strs.add(new OverlaySprite(sp, 0, 0, 0, r, forWhom, sp.getMaximumFrames()));
            }
        }
        return strs;
    }
}

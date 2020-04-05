package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;

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
        Sprite sp = new Sprite("dummy", "animal.png", 0, null);
        ArrayList<OverlaySprite> dummyList = new ArrayList<>();
        dummyList.add(new OverlaySprite(sp, 0, 0, null, null));
        return dummyList;
    }

    private static List<OverlaySprite> getAlwaysSprites(Player player, GameData gameData) {
        List<OverlaySprite> strs = new ArrayList<>();
        for (Room r : player.getVisibleMap(gameData)) {
            strs.addAll(getOverlaysForSpritesInRoom(gameData, r.getAlwaysSprites(), r, player));
        }
        return strs;
    }

    protected static List<OverlaySprite> getOverlaysForSpritesInRoom(GameData gameData, List<Sprite> sprites, Room r, Player forWhom) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        double roomX = (double)r.getX();
        double roomY = (double)r.getY();
        double xIncr = 0.75;
        double yIncr = 0.75;

        double gridX = 0;
        double gridY = 0;
        for (Sprite sp : sprites) {
            double finalX = roomX + gridX*xIncr + 0.2;
            double finalY = roomY + gridY*yIncr + 0.2;
            strs.add(new OverlaySprite(sp, finalX, finalY, r, forWhom, sp.getFrames()));
            gridX += xIncr;
            if (gridX >= r.getWidth()) {
                gridY += yIncr;
                gridX = 0;
                if (gridY >= r.getHeight()) {
                    gridY = 0;
                }
            }

        }
        return strs;
    }
}

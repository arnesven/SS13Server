package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class SeeAllVision extends NormalVision {
    @Override
    protected List<OverlaySprite> getSpritesForSpecificVision(Player player, GameData gameData) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        for (Room r : player.getVisibleMap(gameData)) {
           // if (r != player.getPosition()) {
                ArrayList<Sprite> sprites = new ArrayList<>();
                addEventsForRoom(sprites, player, r);
                addActorsForRoom(sprites, player, r);
                addObjectsForRoom(sprites, player, r);
                addItemsForRoom(sprites, player, r);

                if (player.getSettings().get(PlayerSettings.SHOW_ITEMS_IN_MAP_WHEN_DEAD)) {
                    addItemsForRoom(sprites, player, r);
                }

                strs.addAll(getOverlaysForSpritesInRoom(gameData, sprites, r, player));
           // }
        }
        return strs;
    }
}

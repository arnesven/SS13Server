package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class AlsoSeePowerVision extends NormalVision {


    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
            ArrayList<Sprite> sprs = new ArrayList<>();
            addPowerForRoom(sprs, r, gameData);
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sprs, r, player));
        }

    }

    protected static void addPowerForRoom(List<Sprite> sp, Room r, GameData gameData) {
        for (GameObject ob : r.getObjects()) {
            if (ob instanceof ElectricalMachinery) {
                if (!((ElectricalMachinery) ob).isPowered()) {
                    sp.add(new Sprite("nopowerdecal", "decals2.png", 6, new EmptySpriteObject("Low Power")));
                    break;
                }
            }
        }
    }

}

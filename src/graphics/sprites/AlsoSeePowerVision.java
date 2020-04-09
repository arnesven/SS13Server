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

    // TODO: some how all the OverlaySprites for the players current room get added twice
    // TODO: probably once in the super class and once here....
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
                if (!ElectricalMachinery.isPowered(gameData, (ElectricalMachinery) ob)) {
                    sp.add(new Sprite("nopowerdecal", "decals2.png", 6, null));
                    break;
                }
            }
        }
    }

}

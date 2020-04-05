package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.events.Event;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class AIVision extends AlsoSeePowerVision {

    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {

        List<Room> allRooms = new ArrayList<>();
        allRooms.addAll(player.getPosition().getNeighborList());
        for (Room r : player.getPosition().getNeighborList()) {
            for (Room r2 : r.getNeighborList()) {
                for (Room r3: r2.getNeighborList()) {
                    if (!allRooms.contains(r3)) {
                        allRooms.add(r3);
                    }
                }
                if (!allRooms.contains(r2)) {
                    allRooms.add(r2);
                }
            }
        }

        for (Room r : allRooms) {
            if (r != player.getPosition()) {
                ArrayList<Sprite> sp = new ArrayList<>();
                addActorsForRoom(sp, player, r);
                addItemsForRoom(sp, player, r);
                addObjectsForRoom(sp, player, r);
                addPowerForRoom(sp, r, gameData);
                addEventsForRoom(sp, player, r);
                strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, r, player));
            }
        }
    }
}

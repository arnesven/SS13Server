package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;

import java.util.ArrayList;

public class AlsoSeeSpecificRoomVision extends NormalVision {
    private final Room specificRoom;

    public AlsoSeeSpecificRoomVision(Room room) {
        this.specificRoom = room;
    }


    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        ArrayList<Sprite> sp = new ArrayList<>();
        addEventsForRoom(sp, player, specificRoom);
        addObjectsForRoom(sp, player, specificRoom);
        addActorsForRoom(sp, player, specificRoom);
        addItemsForRoom(sp, player, specificRoom);
        strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, specificRoom, player));
    }
}

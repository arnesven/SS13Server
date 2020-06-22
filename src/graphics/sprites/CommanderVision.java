package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.npcs.NPC;
import util.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommanderVision extends NormalVision {

    private final Set<NPC> commanding;

    public CommanderVision(Set<NPC> commanding) {
        this.commanding = commanding;
    }

    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        super.addExtraSensoryPerception(player, gameData, strs);

        Set<Room> alsoSeeRooms = new HashSet<>();
        for (NPC npc : commanding) {
            alsoSeeRooms.add(npc.getPosition());
        }
        alsoSeeRooms.remove(player.getPosition());
        Logger.log("In extra sensory perception for CommanderVision");
        for (Room r : alsoSeeRooms) {
            Logger.log("also see " + r.getName());
            ArrayList<Sprite> sp = new ArrayList<>();
            addActorsForRoom(sp, player, r);
            addObjectsForRoom(sp, player, r);
            addItemsForRoom(sp, player, r);
            addEventsForRoom(sp, player, r);
            List<OverlaySprite> osprs = getOverlaysForSpritesInRoom(gameData, sp, r, player);
            stripFromActionData(osprs);
            strs.addAll(osprs);
        }

    }

    private void stripFromActionData(List<OverlaySprite> osprs) {
        for (OverlaySprite overlaySprite : osprs) {
            overlaySprite.setActionsEnabled(false);
        }
    }
}

package graphics.sprites;

import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.ChangelingCharacter;
import model.map.rooms.Room;

import java.util.ArrayList;

public class AlsoSeeActorsInAdjacentRooms extends NormalVision {

    @Override
    protected void AddStuffForAdjacentRooms(Player player, ArrayList<OverlaySprite> strs, GameData gameData) {
        // Don't do the normal vision stuff
    }

    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        Room room = player.getPosition();
        for (Room r : room.getNeighborList()) {
            ArrayList<Sprite> sp = new ArrayList<>();
            for (Actor a : r.getActors()) {
                if (ChangelingCharacter.isDetectable(a.getAsTarget())) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, r, player));
        }
    }
}

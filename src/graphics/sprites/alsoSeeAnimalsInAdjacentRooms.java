package graphics.sprites;

import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.map.rooms.Room;

import java.util.ArrayList;

public class alsoSeeAnimalsInAdjacentRooms extends NormalVision {

    @Override
    protected void AddStuffForAdjacentRooms(Player player, ArrayList<OverlaySprite> strs, GameData gameData) {
        // Don't do the normal vision stuff.
    }

    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        for (Room r : player.getPosition().getNeighborList()) {
            ArrayList<Sprite> sp = new ArrayList<>();
            for (Actor a : r.getActors()) {
                if (a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof AnimalCharacter)) ||
                    a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof ParasiteCharacter))) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, r, player));
        }
    }
}

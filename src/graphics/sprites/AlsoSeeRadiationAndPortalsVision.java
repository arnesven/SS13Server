package graphics.sprites;

import graphics.OverlaySprite;
import model.GameData;
import model.Player;
import model.events.Event;
import model.events.ambient.RadiationStorm;
import model.map.rooms.Room;
import model.objects.general.DimensionPortal;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class AlsoSeeRadiationAndPortalsVision extends NormalVision {
    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        for (Room r : player.getPosition().getNeighborList()) {
            ArrayList<Sprite> sp = new ArrayList<>();
            for (Event e : r.getEvents()) {
                if (e instanceof RadiationStorm && e.showSpriteInRoom()) {
                    sp.add(e.getRoomSprite(player));
                }
            }
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof DimensionPortal) {
                    sp.add(ob.getSprite(player));
                }
            }
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, r, player));
        }
    }



}

package graphics.sprites;

import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.general.GameItem;
import model.items.general.HidableItem;
import model.map.doors.DoorAnimationEvent;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.HideableObject;
import util.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NormalVision extends OverlaySpriteCollector {

    @Override
    protected List<OverlaySprite> getSpritesForSpecificVision(Player player, GameData gameData) {
        Room r = player.getPosition();
        ArrayList<Sprite> sp = new ArrayList<>();
        addEventsForRoom(sp, player, r);
        addObjectsForRoom(sp, player, r);
        addActorsForRoom(sp, player, r);
        addItemsForRoom(sp, player, r);

        ArrayList<OverlaySprite> strs = new ArrayList<>();
        strs.addAll(getOverlaysForSpritesInRoom(gameData, sp, r, player));

        AddStuffForAdjacentRooms(player, strs, gameData);

        addExtraSensoryPerception(player, gameData, strs);
        Logger.log("Final Overlays:");
        for (OverlaySprite ospr : strs) {
            if (ospr.getSprite().getObjectReference() instanceof DoorAnimationEvent) {
                Logger.log(ospr.getSprite().getName() + " " +
                        ospr.getSprite().getObjectReference().getAbsoluteX() + " " +
                        ospr.getSprite().getObjectReference().getAbsoluteY());
            }
        }

        return strs;
    }

    protected void addExtraSensoryPerception(Player player, GameData gameData,
                                             ArrayList<OverlaySprite> strs) {
    }


    protected void addEventsForRoom(ArrayList<Sprite> sp, Player player, Room r) {
        for (Event e : r.getEvents()) {
            if (e.showSpriteInRoom()) {
                if (e instanceof DoorAnimationEvent) {
                    Logger.log("Found door animation " +
                            e.getAbsoluteX() + ", " + e.getAbsoluteY() + ", " + e.getSprite(player).getName() + ", " + e.getRoomSprite(player).getObjectReference());
                }
                sp.add(e.getRoomSprite(player));
            }
        }
    }

    protected void addObjectsForRoom(ArrayList<Sprite> sprites, Player player, Room r) {
        for (GameObject ob : r.getObjects()) {
            if (ob instanceof HideableObject) {
                if (((HideableObject) ob).isFound()) {
                    sprites.add(ob.getSprite(player));
                }
            } else {
                sprites.add(ob.getSprite(player));
            }
        }
    }

    protected void addActorsForRoom(ArrayList<Sprite> sp, Player player, Room r) {
        List<Actor> l = new ArrayList<>();
        l.addAll(r.getActors());
        Collections.shuffle(l);
        for (Actor a : l) {
            if (a.getCharacter().isVisible()) {
                sp.add(a.getCharacter().getSprite(player));
            }
        }
    }

    protected void addItemsForRoom(ArrayList<Sprite> sp, Player player, Room r) {
        for (GameItem it : r.getItems()) {
            if (it instanceof HidableItem) {
                if (!((HidableItem) it).isHidden()) {
                    sp.add(it.getSprite(player));
                }
            } else {
                sp.add(it.getSprite(player));
            }
        }
    }

    protected void AddStuffForAdjacentRooms(Player player, ArrayList<OverlaySprite> strs, GameData gameData) {
        Room r = player.getPosition();
        Sprite blurredCharacterSprite = new BlurredCharacter().getSprite(player);
        for (Room r2 : r.getNeighborList()) {
            List<Sprite> sp2 = new ArrayList<>();
            for (Actor a : r2.getActors()) {
                if (a.getCharacter().isVisibileFromAdjacentRoom()) {
                    sp2.add(blurredCharacterSprite);
                }
            }
            for (GameObject obj : r2.getObjects()) {
                if (obj.shouldBeSeenWhenNotInRoomBy(player)) {
                    sp2.add(obj.getSprite(player));
                }
            }
            for (Event e : r2.getEvents()) {
                if (e.getSense().sound == SensoryLevel.AudioLevel.VERY_LOUD ||
                        e.getSense().visual == SensoryLevel.VisualLevel.CLEARLY_VISIBLE ||
                        e.getSense().smell == SensoryLevel.OlfactoryLevel.SHARP) {
                    if (e.showSpriteInRoom()) {
                        sp2.add(e.getRoomSprite(player));
                    }
                }
            }
            strs.addAll(getOverlaysForSpritesInRoom(gameData, sp2, r2, player));
        }
    }
}

package graphics.sprites;

import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.*;
import model.events.Event;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.events.ambient.RadiationStorm;
import model.items.general.GameItem;
import model.items.general.HidableItem;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.DimensionPortal;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.HideableObject;
import util.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
public class OverlaySprites {
    public static List<OverlaySprite> seeAllOverlay(Player player, GameData gameData) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();


        for (Room r : player.getVisibleMap(gameData)) {

            ArrayList<Sprite> sprites = new ArrayList<>();
            List<Event> evs = new ArrayList<>();
            evs.addAll(r.getEvents());

            for (Event e : evs) {  // TODO: got a concurrent modification here!!
                sprites.add(e.getSprite(player));
            }

            addActorsForRoom(sprites, player, r);
            addObjectsForRoom(sprites, player, r);

            if (player.getSettings().get(PlayerSettings.SHOW_ITEMS_IN_MAP_WHEN_DEAD)) {
                addItemsForRoom(sprites, player, r);
            }

            strs.addAll(getStringsForSpritesInRoom(gameData, sprites, r, player));

        }
        return strs;
    }



    public static List<OverlaySprite> dummyList() {
        Sprite sp = new Sprite("dummy", "animal.png", 0, null);
        ArrayList<OverlaySprite> dummyList = new ArrayList<>();
        dummyList.add(new OverlaySprite(sp, 0, 0, null, null));
        return dummyList;
    }


//    public static List<OverlaySprite> seeAlarms(Player player, GameData gameData) {
//        ArrayList<OverlaySprite> strs = new ArrayList<>();
//        for (Room r : gameData.getRooms()) {
//            ArrayList<Sprite> sp = new ArrayList<>();
//            List<Event> list = new ArrayList<>();
//            list.addAll(r.getEvents());
//            for (Event e : list) {
//                if (e instanceof ElectricalFire || e instanceof HullBreach) {
//                    sp.add(e.getSprite(player));
//                }
//            }
//
//            ArrayList<Sprite> parasites = new ArrayList<>();
//            for (Actor a : r.getActors()) {
//                if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PirateCharacter) && !a.isDead()) {
//                    sp.add(a.getCharacter().getSprite(player));
//                } else if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof HorrorCharacter)) {
//                    sp.add(a.getCharacter().getSprite(player));
//                } else if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof ParasiteCharacter)) {
//                    parasites.add(a.getCharacter().getSprite(player));
//                }
//            }
//            if (parasites.size() > 2) {
//                sp.addAll(parasites);
//            }
//            strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));
//        }
//        if (strs.isEmpty()) {
//            return dummyList();
//        }
//        return strs;
//    }

    public static List<OverlaySprite> normalVision(Player player, GameData gameData, Room r) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();

        ArrayList<Sprite> sp = new ArrayList<>();
        //addBackgroundForRoom(sp, player, r);

        for (Event e : r.getEvents()) {
            sp.add(e.getSprite(player));
        }

        addObjectsForRoom(sp, player, r);

        addActorsForRoom(sp, player, r);
        addItemsForRoom(sp, player, r);

        strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));

        Sprite blurredCharacterSprite = new BlurredCharacter().getSprite(player);
        for (Room r2 : r.getNeighborList()) {
            List<Sprite> sp2 = new ArrayList<>();
            for (Actor a : r2.getActors()) {
                if (a.getCharacter().isVisibileFromAdjacentRoom()) {
                    sp2.add(blurredCharacterSprite);
                }
            }
            for (Event e : r2.getEvents()) {
                if (e.getSense().sound == SensoryLevel.AudioLevel.VERY_LOUD ||
                        e.getSense().visual == SensoryLevel.VisualLevel.CLEARLY_VISIBLE ||
                        e.getSense().smell == SensoryLevel.OlfactoryLevel.SHARP) {
                    sp2.add(e.getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(gameData, sp2, r2, player));
        }

        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

//    private static void addBackgroundForRoom(ArrayList<Sprite> sp, Player player, Room r) {
//        if (r.hasBackgroundSprite()) {
//            sp.add(r.getBackgroundSprite(player.getClientInfo()));
//        }
//    }

    public static List<OverlaySprite> seeActorsInAdjacentRooms(GameData gameData, Player player, Room room) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
        for (Room r : room.getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (ChangelingCharacter.isDetectable(a.getAsTarget())) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));
            sp.clear();
        }


        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<OverlaySprite> seeRadiationAndPortalsInRoomAndAdjacent(GameData gameData, Player player) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
        for (Room r : player.getPosition().getNeighborList()) {
            for (Event e : r.getEvents()) {
                if (e instanceof RadiationStorm) {
                    sp.add(e.getSprite(player));
                }
            }
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof DimensionPortal) {
                    sp.add(ob.getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));
            sp.clear();
        }
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static Collection<OverlaySprite> seeAnimalsInAdjacentRooms(Player player, GameData gameData) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
        for (Room r : player.getPosition().getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof AnimalCharacter)) ||
                    a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof ParasiteCharacter))) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }

            strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));
            sp.clear();
        }
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<OverlaySprite> seeAIVision(Player player, GameData gameData) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
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
        if (!allRooms.contains(player.getPosition())) {
            allRooms.add(player.getPosition());
        }



        for (Room r : allRooms) {
            addActorsForRoom(sp, player, r);
            addItemsForRoom(sp, player, r);
            addPowerForRoom(sp, r, gameData);
            for (Event e : r.getEvents()) {
                sp.add(e.getSprite(player));
            }
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof DimensionPortal) {
                    sp.add(ob.getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(gameData, sp, r, player));
            sp.clear();
        }


        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }



    private static List<OverlaySprite> getStringsForSpritesInRoom(GameData gameData, List<Sprite> sprites, Room r, Player forWhom) {
        ArrayList<OverlaySprite> strs = new ArrayList<>();
        double roomX = (double)r.getX();
        double roomY = (double)r.getY();
        double xIncr = 0.75;
        double yIncr = 0.75;


        double gridX = 0;
        double gridY = 0;
        for (Sprite sp : sprites) {
            double finalX = roomX + gridX*xIncr + 0.2;
            double finalY = roomY + gridY*yIncr + 0.2;
            String delim = "<overlay-part>";
            String pos = delim +
                    String.format("%1$.1f", finalX) + delim +
                    String.format("%1$.1f", finalY) + delim;
            if (sp.getObjectReference() != null) {
                pos += sp.getObjectReference().getPublicName(forWhom);
            } else {
                pos += "Unknown";
                Logger.log("Could not find object reference for sprite: " + sp.getName());
            }
            strs.add(new OverlaySprite(sp, finalX, finalY, r, forWhom));
            gridX += xIncr;
            if (gridX >= r.getWidth()) {
                gridY += yIncr;
                gridX = 0;
                if (gridY >= r.getHeight()) {
                    gridY = 0;
                }
            }

        }
        return strs;
    }

    private static String getActionDataForSpriteReference(GameData gameData, Sprite sp, Room r, Player forWhom) {
        if (sp.getObjectReference() == null) {
            return "NoRef";
        }
        return Action.makeActionListStringSpecOptions(gameData,
                sp.getObjectReference().getOverlaySpriteActionList(gameData, r, forWhom),
                forWhom);
    }


    private static void addItemsForRoom(ArrayList<Sprite> sp, Player player, Room r) {
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

    private static void addPowerForRoom(List<Sprite> sp, Room r, GameData gameData) {
        for (GameObject ob : r.getObjects()) {
            if (ob instanceof ElectricalMachinery) {
                if (!ElectricalMachinery.isPowered(gameData, (ElectricalMachinery) ob)) {
                    sp.add(new Sprite("nopowerdecal", "decals2.png", 6, null));
                    break;
                }
            }
        }
    }


    private static void addActorsForRoom(ArrayList<Sprite> sp, Player player, Room r) {
        List<Actor> l = new ArrayList<>();
        l.addAll(r.getActors());
        Collections.shuffle(l);
        for (Actor a : l) {
            if (a.getCharacter().isVisible()) {
                sp.add(a.getCharacter().getSprite(player));
            }
        }
    }

    private static void addObjectsForRoom(ArrayList<Sprite> sprites, Player player, Room r) {
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


    public static List<OverlaySprite> seePower(GameData gameData, Player player) {
        List<OverlaySprite> strs = new ArrayList<>();
        for (Room r : gameData.getMap().getRoomsForLevel("ss13")) {
            ArrayList<Sprite> sprs = new ArrayList<>();
            addPowerForRoom(sprs, r, gameData);
            strs.addAll(getStringsForSpritesInRoom(gameData, sprs, r, player));
        }
        return strs;
    }

    public static List<OverlaySprite> getAlwaysSprites(Player player, GameData gameData) {
        List<OverlaySprite> strs = new ArrayList<>();

        for (Room r : player.getVisibleMap(gameData)) {
            strs.addAll(getStringsForSpritesInRoom(gameData, r.getAlwaysSprites(), r, player));
        }
        return strs;

    }
}

package graphics.sprites;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.actions.general.SensoryLevel;
import model.characters.general.*;
import model.events.Event;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.events.ambient.RadiationStorm;
import model.items.general.GameItem;
import model.items.general.HidableItem;
import model.map.Room;
import model.npcs.ParasiteNPC;
import model.npcs.PirateNPC;
import model.objects.general.DimensionPortal;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
public class OverlaySprites {
    public static List<String> seeAllOverlay(Player player, GameData gameData) {
        ArrayList<String> strs = new ArrayList<>();


        for (Room r : gameData.getRooms()) {

            ArrayList<Sprite> sprites = new ArrayList<>();
            for (Event e : r.getEvents()) {  // TODO: got a concurrent modification here!!
                sprites.add(e.getSprite(player));
            }

            addActorsForRoom(sprites, player, r);

            if (player.getSettings().get(PlayerSettings.SHOW_ITEMS_IN_MAP_WHEN_DEAD)) {
                addItemsForRoom(sprites, player, r);
            }

            strs.addAll(getStringsForSpritesInRoom(sprites, r));

        }
        return strs;
    }

    public static List<String> dummyList() {
        Sprite sp = new Sprite("dummy", "animal.png", 0);
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add(sp.getName()+",0.0,0.0");
        return dummyList;
    }


    public static List<String> seeAlarms(Player player, GameData gameData) {
        ArrayList<String> strs = new ArrayList<>();
        for (Room r : gameData.getRooms()) {
            ArrayList<Sprite> sp = new ArrayList<>();
            for (Event e : r.getEvents()) {
                if (e instanceof ElectricalFire || e instanceof HullBreach) {
                    sp.add(e.getSprite(player));
                }
            }

            ArrayList<Sprite> parasites = new ArrayList<>();
            for (Actor a : r.getActors()) {
                if (a instanceof PirateNPC && !a.isDead()) {
                    sp.add(a.getCharacter().getSprite(player));
                } else if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof HorrorCharacter)) {
                    sp.add(a.getCharacter().getSprite(player));
                } else if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof ParasiteCharacter)) {
                    parasites.add(a.getCharacter().getSprite(player));
                }
            }
            if (parasites.size() > 2) {
                sp.addAll(parasites);
            }
            strs.addAll(getStringsForSpritesInRoom(sp, r));
        }
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<String> seeRoom(Player player, GameData gameData, Room r) {
        ArrayList<String> strs = new ArrayList<>();

        ArrayList<Sprite> sp = new ArrayList<>();
        for (Event e : r.getEvents()) {
            if (e.getSense().visual == SensoryLevel.VisualLevel.CLEARLY_VISIBLE) {
                sp.add(e.getSprite(player));
            }
        }

        addActorsForRoom(sp, player, r);
        addItemsForRoom(sp, player, r);


        strs.addAll(getStringsForSpritesInRoom(sp, r));
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<String> seeActorsInAdjacentRooms(Player player, Room room) {
        ArrayList<String> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
        for (Room r : room.getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (ChangelingCharacter.isDetectable(a.getAsTarget())) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(sp, r));
            sp.clear();
        }


        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<String> seeRadiationAndPortalsInRoomAndAdjacent(Player player) {
        ArrayList<String> strs = new ArrayList<>();
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
            strs.addAll(getStringsForSpritesInRoom(sp, r));
            sp.clear();
        }
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static Collection<String> seeAnimalsInAdjacentRooms(Player player, GameData gameData) {
        ArrayList<String> strs = new ArrayList<>();
        ArrayList<Sprite> sp = new ArrayList<>();
        for (Room r : player.getPosition().getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof AnimalCharacter)) ||
                    a.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof ParasiteCharacter))) {
                    sp.add(a.getCharacter().getSprite(player));
                }
            }

            strs.addAll(getStringsForSpritesInRoom(sp, r));
            sp.clear();
        }
        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }

    public static List<String> seeAIVision(Player player, GameData gameData) {
        ArrayList<String> strs = new ArrayList<>();
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
            for (Event e : r.getEvents()) {
                sp.add(e.getSprite(player));
            }
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof DimensionPortal) {
                    sp.add(ob.getSprite(player));
                }
            }
            strs.addAll(getStringsForSpritesInRoom(sp, r));
            sp.clear();
        }


        if (strs.isEmpty()) {
            return dummyList();
        }
        return strs;
    }


    private static List<String> getStringsForSpritesInRoom(ArrayList<Sprite> sprites, Room r) {
        ArrayList<String> strs = new ArrayList<>();
        double roomX = (double)r.getX();
        double roomY = (double)r.getY();
        double xIncr = 0.75;
        double yIncr = 0.75;

        double gridX = 0;
        double gridY = 0;
        for (Sprite sp : sprites) {
            double finalX = roomX + gridX*xIncr;
            double finalY = roomY + gridY*yIncr;
            String pos = "," +
                    String.format("%1$.1f", finalX) + "," +
                    String.format("%1$.1f", finalY);
            strs.add(sp.getName() + pos);
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



}

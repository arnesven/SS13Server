package graphics.sprites;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.actions.general.SensoryLevel;
import model.characters.general.HorrorCharacter;
import model.characters.general.ParasiteCharacter;
import model.events.Event;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.items.general.GameItem;
import model.map.Room;
import model.npcs.ParasiteNPC;
import model.npcs.PirateNPC;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
public class OverlaySprites {
    public static List<String> seeAllOverlay(Player player, GameData gameData) {
        ArrayList<String> strs = new ArrayList<>();


        for (Room r : gameData.getRooms()) {

            ArrayList<Sprite> sprites = new ArrayList<>();
            for (Event e : r.getEvents()) {
                sprites.add(e.getSprite(player));
            }

            for (Actor a : r.getActors()) {
                sprites.add(a.getCharacter().getSprite(player));
            }


            if (player.getSettings().get(PlayerSettings.SHOW_ITEMS_IN_MAP_WHEN_DEAD)) {
                for (GameItem it : r.getItems()) {
                    sprites.add(it.getSprite(player));
                }
            }

            strs.addAll(getStringsForSpritesInRoom(sprites, r));



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

    public static List<String> dummyList() {
        Sprite sp = new Sprite("dummy", "animal.png", 0);
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add("dummy,0.0,0.0");
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
                } else if (a.getCharacter() instanceof HorrorCharacter) {
                    sp.add(a.getCharacter().getSprite(player));
                } else if (a.getCharacter() instanceof ParasiteCharacter) {
                    parasites.add(a.getCharacter().getSprite(player));
                }
            }
            if (parasites.size() > 3) {
                sp.addAll(parasites);
            }
            strs.addAll(getStringsForSpritesInRoom(sp, r));
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

            for (Actor a : r.getActors()) {
                    sp.add(a.getCharacter().getSprite(player));
            }

            for (GameItem it : r.getItems()) {
                sp.add(it.getSprite(player));
            }

            strs.addAll(getStringsForSpritesInRoom(sp, r));

        return strs;
    }
}

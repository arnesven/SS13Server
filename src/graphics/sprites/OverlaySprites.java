package graphics.sprites;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.events.Event;
import model.items.general.GameItem;
import model.map.Room;

import java.util.ArrayList;
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

        }
        return strs;
    }

    public static List<String> dummyList() {
        Sprite sp = new Sprite("dummy", "animal.png", 0);
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add("dummy,0.0,0.0");
        return dummyList();
    }
}

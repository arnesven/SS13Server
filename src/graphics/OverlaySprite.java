package graphics;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.List;

public class OverlaySprite {
    private final Sprite sprite;
    private final double x;
    private final double y;
    private static final String delim = "<overlay-part>";
    private final Room room;
    private final Player forWhom;

    public OverlaySprite(Sprite sp, double x, double y, Room r, Player forWhom) {
        this.sprite = sp;
        this.x = x;
        this.y = y;
        this.room = r;
        this.forWhom = forWhom;
    }


    public String getStringRepresentation(GameData gameData) {
        String actiondata = "NoRef";
        if (sprite.getObjectReference() != null) {
            actiondata = Action.makeActionListStringNoOptions(gameData,
                    sprite.getObjectReference().getOverlaySpriteActionList(gameData, room, forWhom),
                    forWhom);
        }
        return sprite.getName() + delim +  String.format("%1$.1f", x) + delim + String.format("%1$1f", y) +
                delim + sprite.getObjectReference().getPublicName(forWhom) + delim + actiondata;
    }


    public static String join(List<OverlaySprite> overlayStrings, GameData gameData) {
        StringBuffer buf = new StringBuffer("[");

        int count = 1;
        for (OverlaySprite os : overlayStrings) {
            buf.append(os.getStringRepresentation(gameData));
            if (count < overlayStrings.size()) {
                buf.append("<list-part>");
            }
            count++;
        }
        buf.append("]");
        return buf.toString();
    }

}

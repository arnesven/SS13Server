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
    private final int frames;

    public OverlaySprite(Sprite sp, double x, double y, Room r, Player forWhom, int frames) {
        this.sprite = sp;
        this.x = x;
        this.y = y;
        this.room = r;
        this.forWhom = forWhom;
        this.frames = frames;
    }

    public OverlaySprite(Sprite sp, double x, double y, Room r, Player forWhom) {
        this(sp, x, y, r, forWhom, 1);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getStringRepresentation(GameData gameData) {
        String actiondata = "NoRef";
        if (sprite.getObjectReference() != null && forWhom.getsActions()) {
            actiondata = Action.makeActionListStringSpecOptions(gameData,
                    sprite.getObjectReference().getOverlaySpriteActionList(gameData, room, forWhom),
                    forWhom);
        }
        String spriteObjName = "Unknown";
        if (sprite.getObjectReference() != null) {
            spriteObjName = sprite.getObjectReference().getPublicName(forWhom);
        }

        return sprite.getName() + delim +  String.format("%1$.1f", x) + delim + String.format("%1$1f", y) +
                delim + spriteObjName + delim + actiondata + delim + this.frames + delim + this.getRoom().getID();
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

    public Room getRoom() {
        return room;
    }
}

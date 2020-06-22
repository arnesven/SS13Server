package graphics;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;
import util.Logger;

import java.awt.geom.Point2D;
import java.util.List;

public class OverlaySprite {
    private final Sprite sprite;
    private final double x;
    private final double y;
    private final double z;
    private static final String delim = "<o-p>";
    private final Room room;
    private final Player forWhom;
    private final int frames;
    private boolean actionsEnabled;

    public OverlaySprite(Sprite sp, double x, double y, double z, Room r, Player forWhom, int frames) {
        this.sprite = sp;
        this.x = x;
        this.y = y;
        this.z = z;
        this.room = r;
        this.forWhom = forWhom;
        this.frames = frames;
        actionsEnabled = true;
    }

    public OverlaySprite(Sprite sp, double x, double y, double z, Room r, Player forWhom) {
        this(sp, x, y, z, r, forWhom, 1);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getStringRepresentation(GameData gameData) {
        String actiondata = "NoRef";
        if (sprite.getObjectReference() != null && forWhom.getsActions() && actionsEnabled) {
            actiondata = Action.makeActionListStringSpecOptions(gameData,
                    sprite.getObjectReference().getOverlaySpriteActionList(gameData, room, forWhom),
                    forWhom);
        }
        String spriteObjName = "Unknown";
        String preferredRelPos = "ANY";
        if (sprite.getObjectReference() != null) {
            spriteObjName = sprite.getObjectReference().getPublicName(forWhom);
            RelativePositions relPos = sprite.getObjectReference().getPreferredRelativePosition();
            if (relPos != null) {
                if (relPos.isRelational()) {
                    preferredRelPos = relPos.getRelationString(forWhom);
                } else {
                    Point2D point = relPos.getPreferredRelativePosition(gameData, forWhom, getRoom());
                    if (relPos != null) {
                        preferredRelPos = String.format("%1.2f,%1.2f", point.getX(), point.getY());
                    }
                }
            }
        }

        int roomid = -1;
        if (this.getRoom() != null) {
            if (sprite.getObjectReference() == null || !sprite.getObjectReference().hasAbsolutePosition()) {
                roomid = this.getRoom().getID();
            }
        }

        return sprite.getName() +
                delim + String.format("%1$.1f", x) +
                delim + String.format("%1$.1f", y) +
                delim + String.format("%1$.1f", z) +
                delim + sprite.getWidth() +
                delim + sprite.getHeight() +
                delim + spriteObjName + delim + actiondata +
                delim + this.frames +
                delim + roomid +
                delim + sprite.isLooping() +
                delim + preferredRelPos;
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

    public void setActionsEnabled(boolean actionsEnabled) {
        this.actionsEnabled = actionsEnabled;
    }
}

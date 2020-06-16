package model.map.rooms;

import model.GameData;
import model.Player;

import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class RelativePositions implements Serializable {
    public static final RelativePositions LOWER_LEFT_CORNER = new LowerLeftCorner();
    public static final RelativePositions MID_RIGHT = new MidRight();
    public static final RelativePositions CENTER = new Center();
    public static final RelativePositions MID_TOP = new MidTop();

    public abstract Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r);

    private static class LowerLeftCorner extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(0.0, r.getHeight());
        }
    }

    private static class MidRight extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(r.getWidth(), ((double)r.getHeight()) /2.0);
        }
    }

    private static class Center extends RelativePositions {

        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(((double)r.getWidth()) / 2.0, ((double)r.getWidth())/ 2.0);
        }
    }

    private static class MidTop extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return  new Point2D.Double(((double)r.getWidth()) / 2.0, 0.0);
        }
    }
}

package model.map.rooms;

import model.GameData;
import model.Player;

import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class RelativePositions implements Serializable {
    public static final RelativePositions LOWER_LEFT_CORNER = new LowerLeftCorner();

    public abstract Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r);

    private static class LowerLeftCorner extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(0.0, r.getHeight());
        }
    }
}

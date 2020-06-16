package model.map.rooms;

import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.objects.general.GameObject;

import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class RelativePositions implements Serializable {
    public static final RelativePositions LOWER_LEFT_CORNER = new LowerLeftCorner();
    public static final RelativePositions MID_RIGHT = new MidRight();
    public static final RelativePositions CENTER = new Center();
    public static final RelativePositions MID_TOP = new MidTop();
    public static final RelativePositions UPPER_RIGHT_CORNER = new UpperRightCorner();
    public static final RelativePositions MID_LEFT = new MidLeft();
    public static final RelativePositions UPPER_LEFT_CORNER = new UpperLeftCorner();
    public static final RelativePositions LOWER_RIGHT_CORNER = new LowerRightCorner();

    public abstract Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r);
    public boolean isRelational() { return false; }
    public String getRelationString(Actor whosAsking) { return ""; }

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
            return new Point2D.Double(((double)r.getWidth()) / 2.0, 0.0);
        }
    }

    private static class UpperRightCorner extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(r.getWidth(), 0.0);
        }
    }


    private static class MidLeft extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(0.0, ((double)r.getHeight())/2.0);
        }
    }

    private static class UpperLeftCorner extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(0, 0);
        }
    }


    private static class LowerRightCorner extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(r.getWidth(), r.getHeight());
        }
    }


    public static abstract class RelationalPosition extends RelativePositions {

        private final SpriteObject related;

        public RelationalPosition(SpriteObject sprobj) {
            this.related = sprobj;
        }

        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double();
        }

        @Override
        public boolean isRelational() {
            return true;
        }

        @Override
        public String getRelationString(Actor whosAsking) {
            return getRelation() + "-of-" + related.getPublicName(whosAsking);
        }

        protected abstract String getRelation();
    }

    public static class WestOf extends RelationalPosition {
        public WestOf(SpriteObject sprobj) {
            super(sprobj);
        }

        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return null;
        }

        @Override
        protected String getRelation() {
            return "W";
        }
    }


}
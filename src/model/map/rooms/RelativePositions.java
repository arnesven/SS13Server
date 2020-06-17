package model.map.rooms;

import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.map.doors.Door;
import util.MyRandom;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;

public abstract class RelativePositions implements Serializable {
    public static final RelativePositions LOWER_LEFT_CORNER = new LowerLeftCorner();
    public static final RelativePositions UPPER_LEFT_CORNER = new UpperLeftCorner();
    public static final RelativePositions LOWER_RIGHT_CORNER = new LowerRightCorner();
    public static final RelativePositions UPPER_RIGHT_CORNER = new UpperRightCorner();
    public static final RelativePositions CENTER = new Center();
    public static final RelativePositions MID_RIGHT = new MidRight();
    public static final RelativePositions MID_TOP = new MidTop();
    public static final RelativePositions MID_LEFT = new MidLeft();
    public static final RelativePositions MID_BOTTOM = new MidBottom();

    public static RelativePositions getRandomAnchorPoint() {
        return MyRandom.sample(List.of(LOWER_LEFT_CORNER, UPPER_LEFT_CORNER,
                LOWER_RIGHT_CORNER, UPPER_RIGHT_CORNER, CENTER, MID_TOP,
                MID_RIGHT, MID_LEFT, MID_BOTTOM));
    }

    public abstract Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r);
    protected boolean isAnchorPoint() { return true; }
    public final boolean isRelational() {return !isAnchorPoint(); }
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
            return new Point2D.Double(((double)r.getWidth()) / 2.0, ((double)r.getHeight())/ 2.0);
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


    private static class MidBottom extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(((double)r.getWidth()) / 2.0, r.getHeight());
        }
    }

    public static class Random extends RelativePositions {
        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double();
        }

        @Override
        protected boolean isAnchorPoint() {
            return false;
        }

        @Override
        public String getRelationString(Actor whosAsking) {
            return "random";
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
        protected boolean isAnchorPoint() {
            return false;
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
        protected String getRelation() {
            return "W";
        }
    }


    public static class NorthOf extends RelationalPosition {

        public NorthOf(SpriteObject sprobj) {
            super(sprobj);
        }

        @Override
        protected String getRelation() {
            return "N";
        }
    }

    public static class EastOf extends RelationalPosition {
        public EastOf(SpriteObject pod) {
            super(pod);
        }

        @Override
        protected String getRelation() {
            return "E";
        }
    }

    public static class SouthOf extends RelationalPosition {
        public SouthOf(SpriteObject obj) {
            super(obj);
        }


        @Override
        protected String getRelation() {
            return "S";
        }
    }

    public static class InProximityOf extends RelationalPosition {
        public InProximityOf(SpriteObject usingObject) {
            super(usingObject);
        }

        @Override
        protected String getRelation() {
            return "P";
        }
    }


    public static class CloseToDoor extends RelativePositions {
        private final Door d;

        public CloseToDoor(Door d) {
            this.d = d;
        }

        @Override
        public Point2D getPreferredRelativePosition(GameData gameData, Player forWhom, Room r) {
            return new Point2D.Double(d.getX() - r.getX(), d.getY() - r.getY());
        }
    }
}

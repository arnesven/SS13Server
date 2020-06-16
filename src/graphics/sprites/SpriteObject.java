package graphics.sprites;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;

import java.awt.geom.Point2D;
import java.util.List;

public interface SpriteObject {

    Sprite getSprite(Actor whosAsking);
    String getPublicName(Actor whosAsking);
    List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom);
    void setAbsolutePosition(double x, double y, double z);
    double getAbsoluteX();
    double getAbsoluteY();
    double getAbsoluteZ();
    boolean hasAbsolutePosition();


    static double distance(SpriteObject o1, SpriteObject o2) {
        return Math.sqrt(Math.pow(o1.getAbsoluteX() - o2.getAbsoluteX(), 2) +
                Math.pow(o1.getAbsoluteY() - o2.getAbsoluteY(), 2));
    }


    SpriteObject BLANK = new SpriteObject() {
        @Override
        public Sprite getSprite(Actor whosAsking) {
            return Sprite.blankSprite();
        }

        @Override
        public String getPublicName(Actor whosAsking) {
            return null;
        }

        @Override
        public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
            return null;
        }

        @Override
        public void setAbsolutePosition(double x, double y, double z) {

        }

        @Override
        public double getAbsoluteX() {
            return 0;
        }

        @Override
        public double getAbsoluteY() {
            return 0;
        }

        @Override
        public double getAbsoluteZ() {
            return 0;
        }

        @Override
        public boolean hasAbsolutePosition() {
            return false;
        }

        @Override
        public String getEffectIdentifier(Actor whosAsking) {
            return getSprite(whosAsking).getName();
        }
    };


    default String getEffectIdentifier(Actor whosAsking) {
        return getPublicName(whosAsking) + getSprite(whosAsking).getName();
    }

    default void setPreferredRelativePosition(RelativePositions lowerLeftCorner) {

    }

    default RelativePositions getPreferredRelativePosition() {
        return null;
    }
}

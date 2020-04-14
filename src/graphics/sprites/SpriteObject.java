package graphics.sprites;

import graphics.ClientInfo;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.List;

public interface SpriteObject {


    Sprite getSprite(Actor whosAsking);
    String getPublicName(Actor whosAsking);
    List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom);
    void setAbsolutePosition(double x, double y);
    double getAbsoluteX(ClientInfo clientInfo);
    double getAbsoluteY(ClientInfo clientInfo);
    boolean hasAbsolutePosition();



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
        public void setAbsolutePosition(double x, double y) {

        }

        @Override
        public double getAbsoluteX(ClientInfo clientInfo) {
            return 0;
        }

        @Override
        public double getAbsoluteY(ClientInfo clientInfo) {
            return 0;
        }

        @Override
        public boolean hasAbsolutePosition() {
            return false;
        }
    };


}

package graphics.sprites;

import graphics.ClientInfo;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class BlurredCharacter implements SpriteObject {
    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("blurredchar", "human.png", 138, this);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Somebody";
    }


    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
    }

    @Override
    public boolean hasAbsolutePosition() {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
        //TODO
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
    public String getEffectIdentifier(Actor whosAsking) {
        return getSprite(whosAsking).getName();
    }
}

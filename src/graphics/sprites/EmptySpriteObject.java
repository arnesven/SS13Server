package graphics.sprites;

import graphics.ClientInfo;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class EmptySpriteObject implements SpriteObject {
    private final String label;

    public EmptySpriteObject(String label) {
        this.label = label;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return null;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return label;
    }

    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
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
}

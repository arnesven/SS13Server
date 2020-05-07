package model.objects.recycling;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.RecycleAction;
import model.events.animation.AnimatedSprite;
import model.map.rooms.StationRoom;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class TrashBin extends GameObject {
    private boolean animating;
    private boolean rejecting;
    private int lastUsedIn;

    public TrashBin(StationRoom stationRoom) {
        super("Trash Bin", stationRoom);
        animating = false;
        rejecting = false;
        lastUsedIn = -1;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (animating) {
            return new AnimatedSprite("trashcanani", "trashcan.png", 0, 1, 32, 32, this, 14, false);
        } else if (rejecting) {
            return new Sprite("trashcanreject", "trashcan.png", 7, 0, this);
        }

        return new Sprite("trashcannormal", "trashcan.png", 0, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        at.add(new RecycleAction(this));
    }

    public void setAnimating(boolean b) {
        this.animating = b;
    }

    public void setRejecting(boolean b) {
        this.rejecting = b;
    }

    public void reset() {
        animating = false;
        rejecting = false;
    }

    public void setLastUsedInRound(int round) {
        lastUsedIn = round;
    }

    public int getLastUsedIn() {
        return lastUsedIn;
    }
}

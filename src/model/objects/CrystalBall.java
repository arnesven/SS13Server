package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.WalkUpToObjectAction;
import model.fancyframe.CrystalBallFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.map.rooms.WizardDinghyRoom;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.HashSet;

public class CrystalBall extends GameObject {
    private final HashSet<Room> connectedRooms;
    private boolean inUse;

    public CrystalBall(Room room) {
        super("Crystal Ball", room);
        this.connectedRooms = new HashSet<Room>();
        inUse = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (inUse) {
            return new Sprite("crystalballinuse", "wizardstuff.png", 0, this);
        }
        return new Sprite("crystalball", "wizardstuff.png", 1, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        try {
            connectedRooms.add(gameData.getRoom("Chapel"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        super.addSpecificActionsFor(gameData, cl, at);
        if (cl instanceof Player) {
            at.add(new WalkUpToObjectAction(gameData, cl, this, "Peer into") {
                @Override
                protected FancyFrame getFancyFrame(Actor performingClient, GameData gameData, GameObject someObj) {
                    return new CrystalBallFancyFrame((Player) performingClient, gameData, CrystalBall.this);
                }
            });
        }
    }

    public HashSet<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}

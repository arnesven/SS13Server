package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.DragAction;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.DetachableObject;
import model.objects.DraggableObject;

import java.util.ArrayList;


public abstract class DetachableElectricalMachinery extends ElectricalMachinery implements DetachableObject, DraggableObject {

    private boolean isDetached;

    public DetachableElectricalMachinery(String name, Room r) {
        super(name, r);
        isDetached = false;
    }

    protected abstract Sprite getNormalSprite(Player whosAsking);

    @Override
    public void detachYourself(GameData gameData, Actor performer) {
        isDetached = true;
    }

    @Override
    public boolean isPowered() {
        if (!isDetached) {
            return super.isPowered();
        }
        return false;
    }

    @Override
    public boolean canBeDragged() {
        return isDetached;
    }

    @Override
    public final Sprite getSprite(Player whosAsking) {
        Sprite sp = getNormalSprite(whosAsking);
        if (isDetached) {
            sp.setRotation(90);
        }
        return sp;
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (canBeDragged()) {
            DragAction drag = new DragAction(cl);
            at.add(drag);
        }
    }
}

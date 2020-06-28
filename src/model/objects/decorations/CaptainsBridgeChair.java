package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.DragAction;
import model.actions.general.Action;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.items.general.MedKit;
import model.items.weapons.Weapon;
import model.map.rooms.BridgeRoom;
import model.objects.BridgeChair;
import model.objects.DetachableObject;
import model.objects.DraggableObject;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class CaptainsBridgeChair extends BridgeChair implements Target, Locatable, DetachableObject, DraggableObject {
    private boolean isDetached;

    public CaptainsBridgeChair(String captain, BridgeRoom bridgeRoom) {
        super(captain, bridgeRoom);
        isDetached = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("capsbridgechair", "chairs.png", 5, 11, this);
    }

    @Override
    public String getName() {
        return "Captain's Bridge Chair";
    }

    @Override
    public boolean isTargetable() {
        return true;
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item, GameData gameData) {
        return false;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public double getMaxHealth() {
        return 0;
    }

    @Override
    public boolean hasSpecificReaction(MedKit objectRef) {
        return false;
    }

    @Override
    public void addToHealth(double d) {

    }

    @Override
    public List<GameItem> getItems() {
        return null;
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damager, GameData gameData) {

    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public boolean isHealable() {
        return false;
    }

    @Override
    public boolean canBeInteractedBy(Actor performingClient) {
        return true;
    }

    @Override
    public void detachYourself(GameData gameData, Actor performer) {
        isDetached = true;
        this.setPreferredRelativePosition(null);
    }

    @Override
    public String getDetachingDescription() {
        return "The Captain's Chair came lose from the floor!";
    }

    @Override
    public int getDetachTimeRounds() {
        return 1;
    }

    @Override
    public boolean canBeDragged() {
        return isDetached;
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

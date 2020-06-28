package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.objectactions.BuyAlcoholFromServerAction;
import model.characters.crew.BartenderCharacter;
import model.characters.general.BAR2D2Character;
import model.characters.general.GameCharacter;
import model.items.foods.Beer;
import model.items.foods.BoozeStack;
import model.items.foods.Vodka;
import model.items.foods.Wine;
import model.items.general.ItemStack;
import model.items.general.Locatable;
import model.map.rooms.Room;
import model.objects.DetachableObject;
import model.objects.DraggableObject;
import model.objects.general.DispenserObject;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by erini02 on 15/11/16.
 */
public class Refrigerator extends DispenserObject implements Locatable, DraggableObject, DetachableObject {
    private boolean isDetached;
    private String descr;

    public Refrigerator(Room pos) {
        super("Refrigerator", pos);
        setHealth(4.0);
        setMaxHealth(4.0);
        for (int i = 10; i > 0; i--) {
            addItem(new Vodka());
        }
        for (int i = 10; i > 0; --i) {
            addItem(new Beer());
        }
        for (int i = 10; i > 0; --i) {
            addItem(new Wine());
        }
        isDetached = false;

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("cabinet", "closet.png", 45, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (canServe(cl)) {
            super.addSpecificActionsFor(gameData, cl, at);
        }

        if (isAServerInRoom()) {
            Actor server = getServerInRoom();
            at.add(new BuyAlcoholFromServerAction(server, this));
        }

    }



    private Actor getServerInRoom() {
        for (Actor a : getPosition().getActors()) {
            if (canServe(a)) {
                return a;
            }
        }
        return null;
    }

    private boolean isAServerInRoom() {
        return getServerInRoom() != null;
    }

    private static boolean canServe(Actor whosAsking) {
        return whosAsking.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof BartenderCharacter) ||
                whosAsking.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof BAR2D2Character);
    }

    @Override
    public String getName() {
        return getBaseName();
    }

    @Override
    public void detachYourself(GameData gameData, Actor performer) {
        if (MyRandom.nextDouble() < 0.01) { // TODO change to 0.5
            isDetached = true;
            descr = "The refrigerator came lose from the floor!";
        } else {
            descr = "The refrigerator broke! All its content spilled out on the floor.";
            dropAllItemsOnFloor();
        }
    }

    private void dropAllItemsOnFloor() {
        Collections.shuffle(this.getInventory());

        BoozeStack stack = new BoozeStack();
        while (!getInventory().isEmpty()) {
            stack.addItem(getInventory().remove(0));
            if (stack.size() > MyRandom.nextInt(5)+5) {
                getPosition().addItem(stack);
                stack = new BoozeStack();
            }
        }


    }

    @Override
    public String getDetachingDescription() {
        return descr;
    }

    @Override
    public int getDetachTimeRounds() {
        return 1;
    }

    @Override
    public boolean canBeDragged() {
        return isDetached;
    }
}

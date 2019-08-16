package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.BuyAlcoholFromServerAction;
import model.characters.crew.BartenderCharacter;
import model.characters.general.BAR2D2Character;
import model.characters.general.GameCharacter;
import model.items.foods.Beer;
import model.items.foods.Vodka;
import model.items.foods.Wine;
import model.map.rooms.Room;
import model.objects.general.DispenserObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 15/11/16.
 */
public class Refrigerator extends DispenserObject {
    public Refrigerator(Room pos) {
        super("Refrigerator", pos);
        for (int i = 4; i > 0; i--) {
            addItem(new Vodka());
        }
        for (int i = 5; i > 0; --i) {
            addItem(new Beer());
        }
        for (int i = 5; i > 0; --i) {
            addItem(new Wine());
        }

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

}

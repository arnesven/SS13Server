package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.items.general.GameItem;
import model.items.general.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BoozeStack extends GameItem {
    private final List<GameItem> innerItems;

    public BoozeStack() {
        super("Bunch of Booze", 0.0, false, 0);
        this.innerItems = new ArrayList<>();
    }

    public void addItem(GameItem it) {
        innerItems.add(it);
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        for (GameItem it : innerItems) {
            to.getCharacter().giveItem(it, from);
        }
        to.getItems().remove(this);
    }

    @Override
    public GameItem clone() {
        throw new IllegalStateException("BoozeStack should not be cloned!");
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A whole pile of booze. Enough for you and several of your friends to get seriously hammered.";
    }

    public int size() {
        return innerItems.size();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("boozestack", "drinks.png", 14, 15, this);
    }
}

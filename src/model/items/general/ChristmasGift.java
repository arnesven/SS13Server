package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/11/16.
 */
public class ChristmasGift extends GameItem {
    private final GameItem innerItem;

    public ChristmasGift(GameItem sample) {
        super("Christmas Gift", sample.getWeight(), false, sample.getCost());
        innerItem = sample;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (innerItem.getWeight() < 0.5) {
            return new Sprite("christmasgiftsmall", "items.png", 4, this);
        } else if (innerItem.getWeight() < 1.5) {
            return new Sprite("chrustmasgiftmedium", "items.png", 5, this);
        }
        return new Sprite("chrustmasgiftbif", "items.png", 6, this);
    }

    @Override
    public GameItem clone() {
        throw new UnsupportedOperationException("Should never clone a gift!");
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        at.add(new UnwrapAction());
    }

    public GameItem getInner() {
        return innerItem;
    }

    @Override
    public GameItem getTrueItem() {
        return getInner();
    }

    private class UnwrapAction extends Action {

        public UnwrapAction() {
            super("Unwrap gift", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "unwrapped a gift";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You unwrapped the gift - a " + innerItem.getPublicName(performingClient) + "! Just what you wanted!");
            performingClient.getItems().remove(ChristmasGift.this);
            performingClient.addItem(ChristmasGift.this.getInner(), null);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.UnpackItemAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/11/16.
 */
public class ChristmasGift extends UnpackableItem {

    public ChristmasGift(GameItem sample) {
        super("Christmas Gift", sample.getWeight(), false, sample.getCost());
        super.addInnerItem(sample);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (super.getTotalWeightOfInner() < 0.5) {
            return new Sprite("christmasgiftsmall", "items.png", 4, this);
        } else if (getTotalWeightOfInner() < 1.5) {
            return new Sprite("chrustmasgiftmedium", "items.png", 5, this);
        }
        return new Sprite("chrustmasgiftbif", "items.png", 6, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A wrapped present. What could be inside?";
    }

    @Override
    public GameItem clone() {
        throw new UnsupportedOperationException("Should never clone a gift!");
    }

    @Override
    protected UnpackItemAction getUnpackAction(GameData gameData, ArrayList<Action> at, Actor cl) {
        return new UnwrapChristmasGiftAction(this);
    }

    private class UnwrapChristmasGiftAction extends UnpackItemAction {
        public UnwrapChristmasGiftAction(UnpackableItem uit) {
            super("Unwrap gift", uit);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You unwrapped the gift - a " +
                    ChristmasGift.this.getPublicName(performingClient) + "! Just what you wanted!");
            super.execute(gameData, performingClient);
        }
    }
}

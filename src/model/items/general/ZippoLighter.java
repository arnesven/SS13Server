package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.MakeMolotovAndThrowAction;
import model.characters.decorators.OnFireCharacterDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class ZippoLighter extends LightItem {
    private int uses;

    public ZippoLighter() {
        super("Zippo Lighter", 0.1, false, 45);
        this.uses = 8;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("zippo", "items2.png", 3, 4, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action molotov = new MakeMolotovAndThrowAction(cl);
        if (molotov.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(molotov);
        }
        at.add(new SetYourselfOnFireAction());
    }

    @Override
    public GameItem clone() {
        return new ZippoLighter();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good for lighting smokes. Can be used to make a molotov cocktail if you have something flammable in a bottle.";
    }

    private class SetYourselfOnFireAction extends Action {

        public SetYourselfOnFireAction() {
            super("Set Yourself on Fire", SensoryLevel.FIRE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "started burning";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (GameItem.hasAnItemOfClass(performingClient, ZippoLighter.class)) {
                performingClient.addTolastTurnInfo("You set yourself on fire with the Zippo Lighter!");
                performingClient.setCharacter(new OnFireCharacterDecorator(performingClient.getCharacter()));
            } else {
                performingClient.addTolastTurnInfo("Huh? Zippo Lighter wasn't there? " + failed(gameData, performingClient));
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

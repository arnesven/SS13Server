package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.general.GameCharacter;
import model.characters.visitors.AdventurerCharacter;
import model.items.general.GameItem;
import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;

/**
 * Created by erini02 on 18/10/16.
 */
public class BullWhip extends Weapon {
    public BullWhip() {
        super("Bull Whip", 0.75, 0.5, false, 0.5, false, 175);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bullwhip", "weapons2.png", 27, 29, 32, 32, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Useful for keeping cattle, taming lions, or swinging between buildings.";
    }

    @Override
    public GameItem clone() {
        return new BullWhip();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof AdventurerCharacter)) {
            TargetingAction ta = new SnatchAction(cl);
            if (ta.getNoOfTargets() > 0) {
                at.add(ta);
            }
        }
    }


    @Override
    public boolean doAttack(Actor performingClient, Target target, GameData gameData) {
        boolean success;
        if (MyRandom.nextDouble() > 0.5 ||
                performingClient.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof AdventurerCharacter))) {
            success = target.beAttackedBy(performingClient, this, gameData);
        } else {
            success = performingClient.getAsTarget().beAttackedBy(performingClient, this, gameData);
        }


        if (success) {
            usedOnBy(target, performingClient, gameData);
        } else {
            checkOnlyMissHazard(performingClient, gameData, target);
        }
        checkHazard(performingClient, gameData);
        return success;
    }


    private class SnatchAction extends TargetingAction {
        public SnatchAction(Actor actionPerformer) {
            super("Snatch", SensoryLevel.PHYSICAL_ACTIVITY, actionPerformer);
        }

        @Override
        protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
            if (((Actor)target).getCharacter().getItems().size() > 0 && MyRandom.nextDouble() < 0.75) {
                GameItem it = MyRandom.sample(((Actor)target).getCharacter().getItems());
                ((Actor)target).getCharacter().getItems().remove(it);
                performingClient.getCharacter().giveItem(it, target);
                performingClient.addTolastTurnInfo("You snatched a " + it.getPublicName(performingClient) + " from " + ((Actor) target).getPublicName() + " with your whip.");
                ((Actor) target).addTolastTurnInfo(performingClient.getPublicName() + " snatched your " + it.getFullName((Actor)target) + " with his whip!");
            } else {
                performingClient.addTolastTurnInfo("You missed " + ((Actor) target).getPublicName() + " with your whip.");
                ((Actor) target).addTolastTurnInfo(performingClient.getPublicName() + " tried to attack you with a bull whip.");

            }
        }

        @Override
        public boolean isViableForThisAction(Target target2) {
            return target2 instanceof Actor;
        }

        @Override
        protected boolean requiresProximityToTarget() {
            return false;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "used bull whip to snatch an item from ";
        }
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("whip");
    }
}

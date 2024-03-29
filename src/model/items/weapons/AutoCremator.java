package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.events.animation.AnimatedSprite;
import model.items.NoSuchThingException;
import model.items.general.ActorsAshes;
import model.items.general.GameItem;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by erini02 on 26/11/16.
 */
public class AutoCremator extends Weapon {
    public AutoCremator() {
        super("Auto Cremator", 0.75, 0.5, false, 0.5, false, 300);
    }

    @Override
    public GameItem clone() {
        return new AutoCremator();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action cremation = new CremateAction(this, cl);
        if (cremation.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(cremation);
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("autocremator", "items.png", 26, this);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        if (target.isDead() && target instanceof Actor) {
            cremate(target, performingClient);
        }
    }

    private class CremateAction extends TargetingAction {
        private final AutoCremator creamator;

        public CremateAction(AutoCremator autoCremator, Actor actionPerformer) {
            super("Cremate", SensoryLevel.FIRE, actionPerformer);
            this.creamator = autoCremator;
        }

        @Override
        protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
            if (!performingClient.getItems().contains(creamator)) {
                performingClient.addTolastTurnInfo("What, the " + creamator.getPublicName(performingClient) + " is gone!" + failed(gameData, performingClient));
                return;
            }

            try {
                performingClient.getPosition().removeActor((Actor)target);
            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("What? " + target.getName() + "wasn't there anymore! " + failed(gameData, performingClient));
                return;
            }

            cremate(target, performingClient);

         }

        @Override
        public boolean isViableForThisAction(Target target2) {
            return target2 instanceof Actor && target2.isDead() && (((Actor) target2).isHuman() || ((Actor)target2).isAnimal());
        }

        @Override
        protected boolean requiresProximityToTarget() {
            return false;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "cremated " + target.getName();
        }
    }



    private void cremate(Target beingCremated, Actor performingClient) {
        Actor targetAsActor = (Actor)beingCremated;
        performingClient.addItem(new ActorsAshes(targetAsActor), beingCremated);
        String pronoun = (((Actor) beingCremated).getCharacter().getGender()=="Man"?"he":"she");
        performingClient.addTolastTurnInfo("You cremated " + targetAsActor.getBaseName() + ", may " + pronoun + " rest in peace.");

    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good for cremating dead bodies. The ashes of dead crew members should be sent to their relatives (use the mailbox).";
    }

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite beamSprite = new AnimatedSprite("laserbeamred", "laser.png",
                3, 0, 32, 32, null, 11, false);
        beamSprite.setColor(Color.PINK);
        return beamSprite;
    }
}

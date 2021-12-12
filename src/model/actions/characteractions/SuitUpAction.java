package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.PirateCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.suits.*;
import sounds.Sound;
import util.Logger;

import java.util.List;

public class SuitUpAction extends Action implements QuickAction {

    public SuitUpAction() {
        super("Suit Up", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("jumpsuit_equip");
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "suited up";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        FullBodySuit fbs = null;
        TorsoAndShoesSuit torsoAndShoes = null;
        TorsoSuit torso = null;
        HeadGear headGear = null;
        GlovesItem gloves = null;
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof TorsoAndShoesSuit) {
                torsoAndShoes = (TorsoAndShoesSuit)it;
                Logger.log("Found torso and shoes!");
            } else if (it instanceof TorsoSuit) {
                torso = (TorsoSuit)it;
                Logger.log("Found torso!");
            } else if (it instanceof HeadGear) {
                headGear = (HeadGear)it;
                Logger.log("Found headgear!");
            } else if (it instanceof FullBodySuit) {
                fbs = (FullBodySuit)it;
            } else if (it instanceof GlovesItem) {
                gloves = (GlovesItem)it;
            }
        }


        if (torso != null && torso.canBeWornBy(performingClient)) {
            performingClient.getItems().remove(torso);
            performingClient.putOnSuit(torso);
        } else {
            Logger.log("Torso can't be worn");
        }

        if (torsoAndShoes != null && torsoAndShoes.canBeWornBy(performingClient)) {
            performingClient.getItems().remove(torsoAndShoes);
            performingClient.putOnSuit(torsoAndShoes);
        } else {
            Logger.log("Torso and shoes can't be worn");
        }

        if (headGear!= null && headGear.canBeWornBy(performingClient)) {
            performingClient.getItems().remove(headGear);
            performingClient.putOnSuit(headGear);
        } else {
            Logger.log("headgear can't be worn");
        }

        if (fbs != null && fbs.canBeWornBy(performingClient)) {
            performingClient.getItems().remove(fbs);
            performingClient.putOnSuit(fbs);
        } else {
            Logger.log("full body suit can't be worn");
        }

        if (gloves != null && gloves.canBeWornBy(performingClient)) {
            performingClient.getItems().remove(gloves);
            performingClient.putOnSuit(gloves);
        } else {
            Logger.log("gloves can't be worn");
        }

        performingClient.addTolastTurnInfo("Ready for action!");

        if (performingClient.getInnermostCharacter() instanceof PirateCharacter) {
            ((PirateCharacter) performingClient.getInnermostCharacter()).setSuitedUp(true);
        }

    }


    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("securityoffsuitupabi", "interface.png",
                2,   17, null);
    }

    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        return performer.getPosition().getClients();
    }
}

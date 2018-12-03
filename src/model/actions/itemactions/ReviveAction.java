package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.Defibrilator;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 02/09/16.
 */
public class ReviveAction extends Action {
    private final Defibrilator defibrilator;
    private Actor target;

    public ReviveAction(GameData gameData, Actor cl, Defibrilator defibrilator) {
        super("Resuscitate", SensoryLevel.OPERATE_DEVICE);
        this.defibrilator = defibrilator;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Used a defibrillator";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption op = super.getOptions(gameData, whosAsking);
        for (Actor a : whosAsking.getPosition().getActors()) {
            if (isHuman(a.getCharacter()) && a.getCharacter().isDead() && !brainRemoved(a.getCharacter())) {
                op.addOption(a.getPublicName());
            }
        }
        return op;
    }

    private boolean brainRemoved(GameCharacter character) {
        return character.getPhysicalBody().hasABrain() == false;
    }

    private boolean isHuman(GameCharacter character) {
        return character.checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof HumanCharacter;
            }
        });
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (MyRandom.nextDouble() > 0.25) {
            target.getCharacter().setHealth(1.0);
            target.addTolastTurnInfo("You were resuscitated by " + performingClient.getPublicName() + "!");
            defibrilator.setCharge(false);
            performingClient.addTolastTurnInfo("You resuscitated " + target.getPublicName() + "!");
            gameData.getGameMode().getMiscHappenings().add("The " + target.getBaseName() +
                    " died, but was brought back to life by the " + performingClient.getBaseName() + ".");
        } else {
            target.addTolastTurnInfo(performingClient.getPublicName() + " failed to resuscitate you.");
            performingClient.addTolastTurnInfo("You tried to resuscitate " + target.getPublicName() + ", but you failed.");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : performingClient.getPosition().getActors()) {
            if (isHuman(a.getCharacter()) && a.getCharacter().isDead() && !brainRemoved(a.getCharacter())) {
                if (args.get(0).equals(a.getPublicName())) {
                    target = a;
                    break;
                }
            }
        }
    }
}

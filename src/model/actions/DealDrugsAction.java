package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.NoSuchThingException;
import model.items.chemicals.CaseOfDrugs;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.npcs.NPC;
import model.npcs.behaviors.MaybeIngestDrugsBehavior;

public class DealDrugsAction extends TargetingAction {

    public DealDrugsAction(Actor actor) {
        super("Deal Drugs", SensoryLevel.PHYSICAL_ACTIVITY, actor);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (target instanceof NPC) {
            try {
                CaseOfDrugs drugCase = GameItem.getItemFromActor(performingClient, new CaseOfDrugs());
                try {
                    ((NPC) target).getCharacter().giveItem(drugCase.extractDose(), performingClient.getAsTarget());
                    ((NPC) target).setActionBehavior(new MaybeIngestDrugsBehavior());
                    performingClient.addItem(new MoneyStack(500), target);
                    performingClient.addTolastTurnInfo("You sold " + target.getName() + " some drugs...");
                } catch (CaseOfDrugs.NoDrugsException e) {
                    performingClient.addTolastTurnInfo("You're out of drugs!");
                }

            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("What? The drug case is gone! " + Action.FAILED_STRING);
            }

        } else {
            performingClient.addTolastTurnInfo("You can't deal drugs to " + target.getName() + "! " + Action.FAILED_STRING);
            if (target instanceof Actor) {
                ((Actor) target).addTolastTurnInfo(performingClient.getPublicName() + " tried to sell you drugs!");
            }
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "dealt drugs";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof Actor && ((Actor) target2).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter);
    }
}

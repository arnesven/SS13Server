package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.crew.StaffAssistantCharacter;
import model.items.general.GameItem;
import model.npcs.NPC;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ImitateAction extends TargetingAction {

    public ImitateAction(Actor actionPerformer) {
        super("Imitate", SensoryLevel.NO_SENSE, actionPerformer);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (!(performingClient instanceof Player)) {
            return;
        }
        performingClient.addTolastTurnInfo("You imitated " + target.getName());
        if (target instanceof Player) {
            Player targetAsPlayer = (Player)target;
            targetAsPlayer.getNextAction().doTheAction(gameData, performingClient);
            targetAsPlayer.addTolastTurnInfo(performingClient.getPublicName() + " imitated you!");
            possiblyAddActions(gameData, performingClient, targetAsPlayer);


        } else if (target instanceof NPC) {
            NPC targetAsNPC = (NPC)target;
            targetAsNPC.getActionBehavior().act(performingClient, gameData);
            possiblyAddActions(gameData, performingClient, targetAsNPC);
        }
    }

    private void possiblyAddActions(GameData gameData, Actor performingClient, Actor targetAsActor) {
        if (performingClient.getInnermostCharacter() instanceof StaffAssistantCharacter) {
            Logger.log("Possibly adding new ability for Staff Assistant");
            ArrayList<Action> acts = new ArrayList<>();
            targetAsActor.getInnermostCharacter().addCharacterSpecificActions(gameData, acts);
            if (acts.size() > 0) {
                StaffAssistantCharacter staffAss = (StaffAssistantCharacter)performingClient.getInnermostCharacter();
                staffAss.addLearnedActions(acts);
                performingClient.addTolastTurnInfo("You learned a new ability from " + targetAsActor.getPublicName() + "!");
            }
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "imitated";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof Actor;
    }
}

package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.characteractions.AttackUsingDefaultWeaponAction;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.general.DoNothingAction;
import model.npcs.NPC;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandedByBehavior implements ActionBehavior {

    private Action nextAction;

    public CommandedByBehavior() {
        nextAction = new DoNothingAction();
    }

    public static void parseAction(List<String> args, GameData gameData, NPC npc, Player commander) {
        List<Action> acts = getActionsFor(gameData, npc);
        Logger.log("NPC-Parsing for " + args);
        Action act = null;
        for (Action a : acts) {
            Logger.log("Action is " + a.getName());
            if (a.getOptions(gameData, npc).getName().equals(args.get(0))) {
                args = args.subList(1, args.size());
                a.setActionTreeArguments(args, npc);
                act = a;
                ((CommandedByBehavior)npc.getActionBehavior()).setNextAction(a);
                break;
            }
        }
        if (act != null) {
            String message = npc.getPublicName(commander) + "'s next action is " + act.getFullName();
            message = message.replace(", Command " + npc.getBaseName() + " (0 AP)", "");
            gameData.getChat().serverInSay(message, commander);
        } else {
            Logger.log(Logger.CRITICAL, "Could not parse action for NPC!");
        }
    }

    private void setNextAction(Action a) {
        this.nextAction = a;
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        nextAction.doTheAction(gameData, npc);
        nextAction = new DoNothingAction();
    }

    public static List<Action> getActionsFor(GameData gameData, NPC npc) {
        List<Action> multiOptionActions = new ArrayList<>();
        multiOptionActions.add(new MoveAction(gameData, npc));
        multiOptionActions.add(new AttackUsingDefaultWeaponAction(npc));


        List<Action> fresult = new ArrayList<>();
        for (Action a : multiOptionActions) {
            if (a.getOptions(gameData, npc).numberOfSuboptions() > 0) {
                fresult.add(a);
            }
        }
        return fresult;
    }

}

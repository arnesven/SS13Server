package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.LabelAction;
import model.actions.MoveAction;
import model.actions.characteractions.AttackUsingDefaultWeaponAction;
import model.actions.general.*;
import model.actions.objectactions.ForceOpenDoorAction;
import model.actions.roomactions.AttackDoorAction;
import model.actions.roomactions.OpenAndMoveThroughFireDoorAction;
import model.npcs.CommandableNPC;
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

        Action act = recursiveParse(args, gameData, npc, commander, acts);

        if (act != null) {
            if (!act.wasPerformedAsQuickAction()) {
                String message = npc.getPublicName(commander) + "'s next action is " + act.getFullName();
                message = message.replace(", Command " + npc.getBaseName() + " (0 AP)", "");
                gameData.getChat().serverInSay(message, commander);
            }
        } else {
            Logger.log(Logger.CRITICAL, "Could not parse action for NPC!");
        }
    }

    private static Action recursiveParse(List<String> args, GameData gameData, NPC npc, Player commander, List<Action> acts) {
        for (Action a : acts) {
            if (a.getOptions(gameData, npc).getName().equals(args.get(0))) {
                if (a instanceof ActionGroup) {
                    return recursiveParse(args.subList(1, args.size()), gameData, npc, commander, ((ActionGroup) a).getActions());
                } else {
                    args = args.subList(1, args.size());
                    a.setActionTreeArguments(args, npc);
                    if (!a.wasPerformedAsQuickAction()) {
                        ((CommandedByBehavior) npc.getActionBehavior()).setNextAction(a);
                    }
                    return a;
                }
            }
        }

        return null;
    }

    public void setNextAction(Action a) {
        this.nextAction = a;
    }
    public Action getNextAction() { return this.nextAction; }

    @Override
    public void act(Actor npc, GameData gameData) {
        nextAction.doTheAction(gameData, npc);
        nextAction = new DoNothingAction();
    }

    public static List<Action> getActionsFor(GameData gameData, NPC npc) {
        List<Action> multiOptionActions = new ArrayList<>();
        multiOptionActions.add(new MoveAction(gameData, npc));
        multiOptionActions.add(new AttackUsingDefaultWeaponAction(npc));
        if (npc.hasInventory()) {
            multiOptionActions.add(new PickUpAction(npc));
            multiOptionActions.add(new DropAction(npc));
        }


        List<Action> fresult = new ArrayList<>();
        for (Action a : multiOptionActions) {
            if (a.getOptions(gameData, npc).numberOfSuboptions() > 0) {
                fresult.add(a);
            }
        }

        if (npc instanceof CommandableNPC) {
            for (Action a : ((CommandableNPC) npc).getExtraActionsFor(gameData, npc)) {
                fresult.add(a);
            }
        }

        if (npc.isHuman()) {
            addFireDoorActions(gameData, npc, fresult);
        }

        ArrayList<Action> charActions = new ArrayList<>();
        npc.getCharacter().addCharacterSpecificActions(gameData, charActions);
        for (Action a : charActions) {
            fresult.add(a);
        }

        fresult.add(0, new LabelAction("In " + npc.getPosition().getName()));

        return fresult;
    }

    private static void addFireDoorActions(GameData gameData, NPC npc, List<Action> acts) {
        ActionGroup doorActions = npc.getPosition().getDoorsActionGroup(gameData, npc);
        acts.add(doorActions);
//        for (Action a : doorActions.getActions()) {
//            if (a instanceof ActionGroup) {
//                for (Action a2 : ((ActionGroup) a).getActions()) {
//                    if (a2 instanceof OpenAndMoveThroughFireDoorAction || a2 instanceof AttackDoorAction || a2 instanceof ForceOpenDoorAction) {
//                        acts.add(a);
//                    }
//                }
//            }
//        }
    }

}

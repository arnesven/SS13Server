package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.robots.RobotNPC;
import util.Logger;
import util.MyStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 16/11/16.
 */
public class MindControlAction extends Action {
    private final GameData gameData;
    private Actor victimOfMindControl;
    private String argsString;
    private ActionBehavior oldActionBehavior = null;

    public MindControlAction(Player cl, GameData gameData) {
        super("Mind Control", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "did something weird";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts =  super.getOptions(gameData, whosAsking);
        for (Actor a : getAllTargetableActors(whosAsking)) {
            ActionOption opt = new ActionOption(a.getPublicName());
            addMindControlActions(gameData, opt, a);
            opts.addOption(opt);
        }

        return opts;
    }

    private void addMindControlActions(GameData gameData, ActionOption opt, Actor victim) {

        Player pl;
        if (victim instanceof Player) {
            pl = (Player)victim;

        } else {
            // It's an NPC, but we simulate it as a player!
            pl = new Player(gameData);
            pl.setCharacter(victim.getCharacter());
        }

        List<Action> acts = pl.getActionList(gameData);
        for (Action a : acts) {
            opt.addOption(a.getOptions(gameData, pl));
        }

    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (victimOfMindControl == null) {
            performingClient.addTolastTurnInfo("What? Target wasn't there anymore? " + Action.FAILED_STRING);
            return;
        }

        Player pl;
        if (victimOfMindControl instanceof Player) {
            pl = (Player)victimOfMindControl;
        } else {
            // It's an NPC, but we simulate it as a player!
            pl = new Player(gameData);
            pl.setCharacter(victimOfMindControl.getCharacter());
        }

        Logger.log(Logger.INTERESTING, performingClient.getBaseName() + " is mind controlling " + victimOfMindControl.getBaseName() + " with the following action string: " + argsString);
        pl.addTolastTurnInfo("What are you doing???");
        pl.parseActionFromString(argsString, gameData);

        if (victimOfMindControl instanceof NPC) {
            oldActionBehavior = ((NPC) victimOfMindControl).getActionBehavior();
            ((NPC) victimOfMindControl).setActionBehavior(new ActionBehavior() {
                @Override
                public void act(NPC npc, GameData gameData) {
                    pl.getNextAction().doTheAction(gameData, victimOfMindControl);
                }
            });
        }
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        if (oldActionBehavior != null) {
            ((NPC) victimOfMindControl).setActionBehavior(oldActionBehavior);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

        victimOfMindControl = null;
        for (Actor a : getAllTargetableActors(performingClient)) {
            if (a.getPublicName().equals(args.get(0))) {
                victimOfMindControl = a;
            }
        }


        argsString = MyStrings.join(args.subList(1, args.size()), ",");
        argsString = argsString.substring(1, argsString.length()-1);

    }

    public List<Actor> getAllTargetableActors(Actor whosAsking) {
        List<Actor> result = new ArrayList<>();
        for (Room r : whosAsking.getPosition().getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (a != whosAsking && canBeMindControlled(a)) {
                    result.add(a);
                }
            }
        }
        for (Actor a : whosAsking.getPosition().getActors()) {
                if (a != whosAsking  && canBeMindControlled(a)) {
                    result.add(a);
                }
        }
        return result;
    }

    private static boolean canBeMindControlled(Actor a) {
        return a.getAsTarget().isTargetable() && !(a instanceof RobotNPC);
    }
}

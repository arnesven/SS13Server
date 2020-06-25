package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.npcs.NPC;
import model.npcs.behaviors.CommandedByBehavior;

import java.util.List;
import java.util.Set;

public class CommandAllAction extends FreeAction {
    private final Set<NPC> commanding;

    public CommandAllAction(Set<NPC> commanding, GameData gameData, Player player) {
        super("Command All", gameData, player);
        this.commanding = commanding;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        opts.addOption("Suit Up");

        return opts;
    }



    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        if (args.get(0).equals("Suit Up")) {
            for (NPC npc : commanding) {
                if (npc.getActionBehavior() instanceof CommandedByBehavior) {
                    CommandedByBehavior.parseAction(args, gameData, npc, p);
                }
            }
        }
    }
}

package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.npcs.NPC;

import java.util.List;
import java.util.Set;

public class CommandAllAction extends FreeAction {
    public CommandAllAction(Set<NPC> commanding, GameData gameData, Player player) {
        super("Command All", gameData, player);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);



        return opts;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {

    }
}

package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.npcs.NPC;

import java.util.List;

public class CantCommandNPCAction extends CommandNPCAction {
    public CantCommandNPCAction(NPC npc, GameData gameData, Player actor) {
        super(npc, gameData, actor);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        return new ActionOption("Can't Command!");
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {

    }

}

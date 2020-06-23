package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.DoNothingAction;
import model.npcs.NPC;
import model.npcs.behaviors.CommandedByBehavior;

import java.util.ArrayList;
import java.util.List;

public class CommandNPCAction extends FreeAction {
    private final NPC npc;
    private final Player commander;

    public CommandNPCAction(NPC npc, GameData gameData, Player p) {
        super("Command " + npc.getBaseName(), gameData, p);
        this.npc = npc;
        this.commander = p;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Action a : CommandedByBehavior.getActionsFor(gameData, npc)) {
            opts.addOption(a.getOptions(gameData, npc));
        }
        return opts;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        CommandedByBehavior.parseAction(args, gameData, npc, p);
        p.refreshClientData();
    }

    @Override
    public Sprite getAbilitySprite() {
        List<Sprite> lst = new ArrayList<>();
        String extra = "";
        lst.add(npc.getCharacter().getUnanimatedSprite(commander));
        if (npc.getActionBehavior() instanceof CommandedByBehavior) {
            if (!(((CommandedByBehavior) npc.getActionBehavior()).getNextAction() instanceof DoNothingAction)) {
                lst.add(new Sprite("greenflag", "interface.png", 0, 17, null));
                extra = "ready";
            }
        }
        lst.add(new Sprite("commandoverlay", "interface.png", 16, 16, null));
        return new Sprite("commnad"+lst.get(0).getName() + extra + "abi", "human.png", 0, lst, null);
    }
}

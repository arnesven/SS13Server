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
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class CommandNPCAction extends FreeAction {
    private final NPC npc;
    private final Player commander;
    private final List<Action> actions;

    public CommandNPCAction(NPC npc, GameData gameData, Player p) {
        super("Command " + npc.getPublicName(p), gameData, p);
        this.npc = npc;
        this.commander = p;
        this.actions = CommandedByBehavior.getActionsFor(gameData, npc);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Action a : actions) {
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
        lst.add(makeHealthSprite(npc));
        return new Sprite("command"+lst.get(0).getName() + extra + npc.getHealth()+ "abi", "human.png", 0, lst, null);
    }

    private Sprite makeHealthSprite(NPC npc) {
        if (npc.isDead()) {
            return new Sprite("dead", "interface.png", 11, 17, null);
        } else {
            double ratio = 1.0 - npc.getHealth() / npc.getMaxHealth();
            int numSprites = 8;
            int spriteX = ((int)Math.floor(ratio * numSprites)) + 3;
            return new Sprite("heatl", "interface.png", spriteX, 17, null);
        }

    }
}

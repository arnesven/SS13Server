package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.CommanderVision;
import model.GameData;
import model.Player;
import model.actions.characteractions.CantCommandNPCAction;
import model.actions.characteractions.CommandAllAction;
import model.actions.characteractions.CommandNPCAction;
import model.actions.characteractions.StartCommandingAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.npcs.CommandableNPC;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.CommandedByBehavior;
import model.npcs.behaviors.MeanderingMovement;
import util.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NPCCommanderDecorator extends CharacterDecorator {

    private int cpRemaining;
    private Set<NPC> commanding;

    public NPCCommanderDecorator(GameCharacter character, int initialCp) {
        super(character, "NPCCommander");
        commanding = new HashSet<>();
        this.cpRemaining = initialCp;
    }

    public static NPCCommanderDecorator getDecorator(GameCharacter gc) {
        if (gc instanceof NPCCommanderDecorator) {
            return (NPCCommanderDecorator) gc;
        } else if (gc instanceof CharacterDecorator) {
            return getDecorator(((CharacterDecorator) gc).getInner());
        }
        return null;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        Logger.log("addCharSpecificActions called for " + getActor().getPublicName(getActor()));
        Action a = new StartCommandingAction(getActor(), commanding, cpRemaining) {
            @Override
            protected boolean canBeCommanded(NPC target2) {
                return canCommand(target2);
            }
        };
        if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 1) {
            at.add(a);
        }
        if (commanding.size() > 0) {
            at.add(new CommandAllAction(commanding, gameData, (Player)getActor()));
        }
        for (NPC npc : commanding) {
            if (npc.getsActions()) {
                at.add(new CommandNPCAction(npc, gameData, (Player) getActor()));
            } else {
                at.add(new CantCommandNPCAction(npc, gameData, (Player)getActor()));
            }
        }
    }

    protected boolean canCommand(NPC target2) {
        return true;
    }

    public boolean addCommandable(NPC target) {
        if (target instanceof CommandableNPC) {
            if (((CommandableNPC) target).getCommandPointCost() > cpRemaining) {
                return false;
            }
            cpRemaining -= ((CommandableNPC) target).getCommandPointCost();
        }
        this.commanding.add(target);
        ActionBehavior cmd = new CommandedByBehavior();
        target.setActionBehavior(cmd);
        target.setMoveBehavior(new MeanderingMovement(0.0));
        return true;
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return new CommanderVision(commanding).getOverlaySprites(player, gameData);
    }
}

package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.CommanderVision;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.CantCommandNPCAction;
import model.actions.characteractions.CommandNPCAction;
import model.actions.characteractions.StartCommandingAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.CommandedByBehavior;
import model.npcs.behaviors.MeanderingMovement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NPCCommanderDecorator extends CharacterDecorator {

    private Set<NPC> commanding;

    public NPCCommanderDecorator(GameCharacter character) {
        super(character, "NPCCommander");
        commanding = new HashSet<>();
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
        Action a = new StartCommandingAction(getActor(), commanding) {
            @Override
            protected boolean canBeCommanded(NPC target2) {
                return canCommand(target2);
            }
        };
        if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
            at.add(a);
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

    public void addCommandable(NPC target) {
        this.commanding.add(target);
        ActionBehavior cmd = new CommandedByBehavior();
        target.setActionBehavior(cmd);
        target.setMoveBehavior(new MeanderingMovement(0.0));
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return new CommanderVision(commanding).getOverlaySprites(player, gameData);
    }
}

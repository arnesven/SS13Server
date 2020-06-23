package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.FreeTargetingAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.NPCCommanderDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.npcs.NPC;
import util.Logger;

import java.util.Set;

public abstract class StartCommandingAction extends FreeTargetingAction {
    public StartCommandingAction(Actor ap, Set<NPC> alreadyCommanding) {
        super("Start Commanding", SensoryLevel.SPEECH, ap);
        getTargets().removeAll(alreadyCommanding);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof NPCCommanderDecorator)) {
            NPCCommanderDecorator command = NPCCommanderDecorator.getDecorator(performingClient.getCharacter());
            command.addCommandable((NPC)target);
        }
        ((Player)performingClient).refreshClientData();
    }

    @Override
    protected boolean requiresProximityToTarget() {
        return false;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Started commanding";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof NPC && !target2.isDead() && canBeCommanded((NPC)target2);
    }

    protected abstract boolean canBeCommanded(NPC target2);

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("startcommandingabi", "weapons2.png", 59, 42, null);
    }
}

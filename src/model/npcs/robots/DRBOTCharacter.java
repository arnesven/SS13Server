package model.npcs.robots;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.TurnOnRobotAction;
import model.actions.general.Action;
import model.characters.general.RobotCharacter;
import model.events.animation.AnimatedSprite;
import model.npcs.NPC;
import model.npcs.behaviors.*;

import java.util.ArrayList;

public class DRBOTCharacter extends RobotCharacter {
    public DRBOTCharacter(Integer id) {
        super("DR-BOT", id, -0.5);
    }


    @Override
    protected Sprite getNormalSprite(Actor whosAsking) {
        if (getActor() instanceof NPC && (((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior)) {
            return new Sprite("drbotinactive", "aibots.png", 13, getActor());
        }
        return new Sprite("drbotnormal", "aibots.png", 14, getActor());
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getActor() instanceof NPC && ((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior) {
            at.add(new TurnDrBotOnAction((NPC)getActor()));
        }
    }

    private class TurnDrBotOnAction extends TurnOnRobotAction {

        public TurnDrBotOnAction(NPC npc) {
            super(npc);
        }

        @Override
        protected ActionBehavior getActionBehavior() {
            return new HealOtherBehavior(1.5);
        }

        @Override
        protected MovementBehavior getNewMovementBehavior() {
            return new MeanderingMovement(0.35);
        }
    }
}

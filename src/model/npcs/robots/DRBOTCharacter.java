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
import java.util.List;

public class DRBOTCharacter extends RobotCharacter {
    private int extraLook;

    public DRBOTCharacter(Integer id) {
        super("DR-BOT", id, -0.5);
        extraLook = -1;
    }


    @Override
    protected Sprite getNormalSprite(Actor whosAsking) {
        if (getActor() instanceof NPC && (((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior)) {
            return new Sprite("drbotinactive", "aibots.png", 13, getActor());
        }
        return makeNormalSprite();
    }

    private Sprite makeNormalSprite() {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(new Sprite("drbotnormal", "aibots.png", 14, getActor()));
        String nameStr = "";
        if (extraLook != -1) {
            nameStr += "" + extraLook;
            sprs.add(new Sprite("drbotextra" + extraLook, "aibots.png", extraLook, getActor()));
        }

        return new Sprite("drbot" + nameStr, "human.png", 0, sprs, getActor());

    }

    @Override
    protected Sprite getBrokenSprite(Actor whosAsking) {
        return new Sprite("drbotbroken", "aibots.png", 61, getActor());
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getActor() instanceof NPC && ((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior) {
            at.add(new TurnDrBotOnAction((NPC)getActor()));
        }
    }

    public void setAntidoteLook() {
        this.extraLook = 73;
    }

    public void setAntiFireLook() {
        this.extraLook = 80;
    }

    public void setAntiRadLook() {
        this.extraLook = 74;
    }

    public void setHealingLook() {
        this.extraLook = -1;
    }

    private class TurnDrBotOnAction extends TurnOnRobotAction {

        public TurnDrBotOnAction(NPC npc) {
            super(npc);
        }

        @Override
        protected ActionBehavior getActionBehavior() {
            return new RunDiagnosticsBehavior();
        }

        @Override
        protected MovementBehavior getNewMovementBehavior() {
            return new StayCloseToSickbayMovementBehavior();
        }
    }
}

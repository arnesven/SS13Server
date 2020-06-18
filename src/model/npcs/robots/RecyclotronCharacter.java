package model.npcs.robots;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.characteractions.TurnOnRobotAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.RobotCharacter;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.*;

import java.util.ArrayList;
import java.util.List;


public class RecyclotronCharacter extends RobotCharacter {

    public RecyclotronCharacter(Room r) {
        super("RecycloTRON", r.getID(), -1.0);
    }

    @Override
    public Sprite getUnanimatedSprite(Actor whosAsking) {
        return new Sprite("recyclotron", "aibots.png", 39, getActor());
    }

    @Override
    protected Sprite getNormalSprite(Actor whosAsking) {
        if (getActor() instanceof Player) {
            return new Sprite("recyclotron", "aibots.png", 39, getActor());
        } else if (getActor() instanceof NPC && !(((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior)) {
            return new AnimatedSprite("recyclotronani", "aibots.png", 40, 0, 32, 32, getActor(), 2, true);

        }
        return new Sprite("recyclotroninactive", "aibots.png", 38, getActor());
    }

    @Override
    protected Sprite getBrokenSprite(Actor whosAsking) {
        return new Sprite("recyclotronbroken", "aibots.png", 62, getActor());
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getActor() instanceof NPC && ((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior) {
            at.add(new TurnOnRecyclotronAction((NPC)getActor()));
        }
    }

    private class TurnOnRecyclotronAction extends TurnOnRobotAction {
       public TurnOnRecyclotronAction(NPC actor) {
            super(actor);
       }

        @Override
        protected ActionBehavior getActionBehavior() {
            return new RecycleItemsOnFloorBehavior();
        }

        @Override
        protected MovementBehavior getNewMovementBehavior() {
            return new MoveIfNoItemsMovement();
        }
    }
}
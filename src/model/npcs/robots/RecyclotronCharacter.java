package model.npcs.robots;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.RobotCharacter;
import model.events.animation.AnimatedSprite;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MoveIfNoItemsMovement;
import model.npcs.behaviors.RecycleItemsOnFloorBehavior;

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
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getActor() instanceof NPC && ((NPC) getActor()).getActionBehavior() instanceof DoNothingBehavior) {
            at.add(new TurnOnRecyclotronAction((NPC)getActor()));
        }
    }

    private class TurnOnRecyclotronAction extends Action {
        private final NPC bot;

        public TurnOnRecyclotronAction(NPC recyclotronCharacter) {
            super("Turn on RecycloTRON", SensoryLevel.OPERATE_DEVICE);
            this.bot = recyclotronCharacter;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "turned on the RecycloTRON";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (bot.getPosition() == performingClient.getPosition()) {
                bot.setMoveBehavior(new MoveIfNoItemsMovement());
                bot.setActionBehavior(new RecycleItemsOnFloorBehavior());
                performingClient.addTolastTurnInfo("You turned the RecycloTRON on.");
            } else {
                performingClient.addTolastTurnInfo("Huh? RecycloTRON no longer there? " + failed(gameData, performingClient));
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
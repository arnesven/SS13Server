package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.ambient.ElectricalFire;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.JungleManNPC;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
public class JungleCampFire extends GameObject {
    public JungleCampFire(Room position, GameData gameData) {
        super("Camp Fire", position);
        gameData.addEvent(new CampFireEvent());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("campfire", "jungle.png", 7, 13);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (GameItem.hasAnItemOfClass(cl, FireExtinguisher.class)) {
            at.add(new PutOutCampFire());
        }
        at.add(new SingASong());
    }

    private class CampFireEvent extends Event {
        @Override
        public void apply(GameData gameData) {
            if (MyRandom.nextDouble() < 0.5 &&
                    noJungleMenInRoom(JungleCampFire.this.getPosition()) &&
                    !JungleCampFire.this.getPosition().hasFire()) {
                ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                fire.startNewEvent(JungleCampFire.this.getPosition());
            }
        }

        private boolean noJungleMenInRoom(Room position) {
            for (Actor a : position.getActors()) {
                if (a instanceof JungleManNPC && !a.isDead()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "";
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }
    }

    private class PutOutCampFire extends Action {

        public PutOutCampFire() {
            super("Put out Camp Fire", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "put out the camp fire";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            JungleCampFire.this.getPosition().removeObject(JungleCampFire.this);
            performingClient.addTolastTurnInfo("You put out the camp fire.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class SingASong extends Action {

        public SingASong() {
            super("Sing Camp Fire Song", SensoryLevel.SPEECH);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "sang kumbaya my lord - kumbaya";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You sang a nice camp fire song: kumbaya my lord - kunbaya!");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

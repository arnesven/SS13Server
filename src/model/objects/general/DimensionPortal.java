package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class DimensionPortal extends GameObject {
    private final Room room;
    private final Room otherDimRoom;

    public DimensionPortal(GameData gameData, Room position, Room destination) {
        super("Portal", position);
        this.room = position;
        otherDimRoom = destination;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("portal", "weapons2.png", 19, 10, 32, 32);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
        at.add(new GoThroughPortalAction(otherDimRoom));
    }

    public void remove() {
        room.getObjects().remove(this);

    }

    private class GoThroughPortalAction extends Action {

        private final Room destination;

        /**
         * @param otherDimRoom
         */
        public GoThroughPortalAction(Room otherDimRoom) {
            super("Go Through Portal", SensoryLevel.PHYSICAL_ACTIVITY);
            this.destination = otherDimRoom;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "went through the portal!";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.moveIntoRoom(destination);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class DimensionPortal extends GameObject {
    private final Room room;
    private final Room otherDimRoom;
    private final String name;

    public DimensionPortal(GameData gameData, Room position, Room destination, String name) {
        super("Portal", position);
        this.room = position;
        otherDimRoom = destination;
        this.name = name;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("portalblue", "weapons2.png", 19, 10, 32, 32, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new GoThroughPortalAction(otherDimRoom, name));
    }

    public void remove() {
        room.getObjects().remove(this);

    }

    private class GoThroughPortalAction extends Action {

        private final Room destination;

        /**
         * @param otherDimRoom
         * @param name
         */
        public GoThroughPortalAction(Room otherDimRoom, String name) {
            super("Go Through " + name + " Portal", SensoryLevel.PHYSICAL_ACTIVITY);
            this.destination = otherDimRoom;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "went through the portal!";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.moveIntoRoom(destination);
            performingClient.addTolastTurnInfo("You went through the portal!");
//            if (performingClient.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof SeeOnlyThisRoomTypeDecorator)) {
//                performingClient.removeInstance((GameCharacter gc ) -> gc instanceof SeeOnlyThisRoomTypeDecorator);
//            }
//            if (destination.getType() == RoomType.outer || destination.getType() == RoomType.derelict) {
//                performingClient.setCharacter(new SeeOnlyThisRoomTypeDecorator(performingClient.getCharacter(), destination.getType()));
//            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

}

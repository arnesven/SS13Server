package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.AirDuctRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class VentObject extends GameObject {
    private final Room toRoom;

    public VentObject(Room here, Room to) {
        super("Air Vent", here);
        this.toRoom = to;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("airvent", "power.png", 10, 12, this);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking.getCharacter().getSize() == GameCharacter.SMALL_SIZE) {
            return super.getPublicName(whosAsking) + " to " + toRoom.getName();
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getCharacter().getSize() == GameCharacter.SMALL_SIZE) {
            at.add(new VentMoveAction(cl));
        }
    }

    private class VentMoveAction extends MoveAction {

        boolean intoVent;

        public VentMoveAction(Actor actor) {
            super(actor);
            String prep = " out to ";
            intoVent = false;
            if (toRoom instanceof AirDuctRoom) {
                prep = " into ";
                intoVent = true;
            }
            setName("Crawl" + prep + toRoom.getName());
            setDestination(toRoom);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            super.execute(gameData, performingClient);
            if (intoVent) {
                performingClient.setCharacter(new SeeAirDuctsDecorator(performingClient.getCharacter()));
            } else {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof SeeAirDuctsDecorator);
            }
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = new ActionOption(getName());
            return opts;
        }


    }

    private class SeeAirDuctsDecorator extends CharacterDecorator {
        public SeeAirDuctsDecorator(GameCharacter character) {
            super(character, "SeeAirDuctsDecorator");
        }

        @Override
        public String getFullName() {
            return super.getFullName() + " (Crawling)";
        }

        @Override
        public List<Room> getVisibleMap(GameData gameData) {
            List<Room> result = super.getVisibleMap(gameData);
            try {
                for (Room r : gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(getPosition()).getName())) {
                    if (r instanceof AirDuctRoom) {
                        result.add(r);
                    }
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            } ;
            return result;
        }
    }
}

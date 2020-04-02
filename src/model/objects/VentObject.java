package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.characters.general.GameCharacter;
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
        at.add(new VentMoveAction(cl));
    }

    private class VentMoveAction extends MoveAction {

        public VentMoveAction(Actor actor) {
            super(actor);
            String prep = " out to ";
            if (toRoom instanceof AirDuctRoom) {
                prep = " into ";
            }
            setName("Crawl" + prep + toRoom.getName());
            setDestination(toRoom);
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = new ActionOption(getName());
            return opts;
        }


    }
}

package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.ElevatorRoom;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/12/16.
 */
public class ElevatorPanel extends ElectricalMachinery {
    private final ElevatorRoom room;

    public ElevatorPanel(ElevatorRoom r) {
        super("Panel", r);
        this.room = r;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("elevatorpanel", "power.png", 6, 10);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new HoldElevatorAction());
    }

    private class HoldElevatorAction extends Action {

        public HoldElevatorAction() {
            super("Hold Elevator", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with the panel";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            room.hold();
            performingClient.addTolastTurnInfo("You held the elevator.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.OnSurgeryTableDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 29/11/16.
 */
public class SurgeryTable extends GameObject {

    private Actor personOnTable;
    private DeadPersonOnTableEvent deadChecker;

    public SurgeryTable(Room sickbay) {
        super("Surgery Table", sickbay);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isOccupied()) {
            List<Sprite> sp = new ArrayList<>();
            Sprite table = new Sprite("surgerytable", "surgery.png", 1, 1);
            table.setRotation(-90.0);
            sp.add(table);
            sp.add(personOnTable.getCharacter().getSprite(whosAsking));
            Sprite sp2 = new Sprite("humanontable", "human.png", 0, 0, 32, 32, sp);
            sp2.setRotation(90.0);
            return sp2;

        }
        return new Sprite("surgerytable", "surgery.png", 1, 1);
    }

    public boolean isOccupied() {
        return personOnTable != null;
    }

    public void takePersonOffTable() {
        personOnTable = null;
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (!isOccupied()) {
            at.add(new GetOnSurgeryTableAction());
        }
        if (cl == personOnTable) {
            at.add(new GetOffSurgeryTableAction());
        }
    }

    public Actor getPersonOnTable() {
        return personOnTable;
    }


    private class GetOnSurgeryTableAction extends Action {

        public GetOnSurgeryTableAction() {
            super("Get on Surgery Table", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "got on the surgery table";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You got up on the surgery table.");
            performingClient.setCharacter(new OnSurgeryTableDecorator(performingClient.getCharacter(), SurgeryTable.this));
            SurgeryTable.this.occupy(performingClient);

            if (deadChecker == null) {
                deadChecker = new DeadPersonOnTableEvent(SurgeryTable.this);
                gameData.addEvent(deadChecker);
            }

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private void occupy(Actor performingClient) {
        this.personOnTable = performingClient;
    }

    private class GetOffSurgeryTableAction extends Action {
        public GetOffSurgeryTableAction() {
            super("Get off Surgery Table", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "got off the surgery table";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            SurgeryTable.this.takePersonOffTable();
            performingClient.addTolastTurnInfo("You got down from the surgery table.");
            performingClient.removeInstance((GameCharacter gc ) -> gc instanceof OnSurgeryTableDecorator);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class DeadPersonOnTableEvent extends Event {
        private final SurgeryTable table;

        public DeadPersonOnTableEvent(SurgeryTable surgeryTable) {
            this.table = surgeryTable;
        }

        @Override
        public void apply(GameData gameData) {
            if (table.isOccupied()) {
                if (table.getPersonOnTable().isDead()) {
                    table.takePersonOffTable();
                }
            }
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
}

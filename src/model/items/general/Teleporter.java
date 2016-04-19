package model.items.general;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.TeleportAction;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class Teleporter extends GameItem {

    private Room coordinates;

    public Teleporter() {
        super("Teleporter", 1.0);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
        super.addYourActions(gameData, at, cl);
        addMarkCoordinatesAction(gameData, at, cl);
        if (coordinates != null) {
            addTeleportAction(gameData, at, cl);
        }
    }

    private void addTeleportAction(GameData gameData, ArrayList<Action> at, Player cl) {
        at.add(new TeleportAction(this));
    }

    private void addMarkCoordinatesAction(GameData gameData, ArrayList<Action> at, final Player cl) {
        at.add(new Action("Mark Coordinates", new SensoryLevel(SensoryLevel.VisualLevel.STEALTHY,
                SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.UNSMELLABLE)) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "Fiddled with teleporter.";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                coordinates = cl.getPosition();
                cl.addTolastTurnInfo("You marked the coordinates for this room in the teleporter.");
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) { }
        });
    }

    @Override
    public Teleporter clone() {
        return new Teleporter();
    }

    public Room getMarked() {
        return coordinates;
    }
}

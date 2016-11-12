package model.items.general;

import graphics.sprites.Sprite;
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
public class Teleporter extends UplinkItem {

    private Room coordinates;
    private boolean used = false;

    public Teleporter() {
        super("Teleporter", 0.3);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("teleporter", "device.png", 4);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
        super.addYourActions(gameData, at, cl);
        if (!used) {
            addMarkCoordinatesAction(gameData, at, cl);
            if (coordinates != null) {
                addTeleportAction(gameData, at, cl);
            }
        }
    }

    @Override
    public String getFullName(Actor whosAsking) {
        if (used) {
            return super.getFullName(whosAsking) + "(no charge)";
        }
        return super.getFullName(whosAsking);
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

    public void setUsed(boolean used) {
        this.used = used;
    }
}

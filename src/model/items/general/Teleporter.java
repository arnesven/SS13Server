package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.TeleportAction;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class Teleporter extends UplinkItem {

    private Room coordinates;
    private int uses = 3;

    public Teleporter() {
        super("Teleporter", 0.3, 200);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("teleporter", "device.png", 4, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (uses > 0) {
            addMarkCoordinatesAction(gameData, at, cl);
            if (coordinates != null) {
                addTeleportAction(gameData, at, cl);
            }
        }
    }

    @Override
    public String getFullName(Actor whosAsking) {
        if (uses == 0) {
            return super.getFullName(whosAsking) + "(no charge)";
        }
        return super.getFullName(whosAsking);
    }

    private void addTeleportAction(GameData gameData, ArrayList<Action> at, Actor cl) {
        at.add(new TeleportAction(this));
    }

    protected void addMarkCoordinatesAction(GameData gameData, ArrayList<Action> at, final Actor cl) {
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

//    public void setUsed(boolean used) {
//        this.used = used;
//    }

    public void useOnce() {
        this.uses--;
    }

    public void setMarked(Room marked) {
        this.coordinates = marked;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good to teleport instantly to a location you have previously marked.";
    }
}

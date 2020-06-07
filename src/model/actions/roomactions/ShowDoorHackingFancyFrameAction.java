package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.DoorHackingFancyFrame;
import model.fancyframe.FancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class ShowDoorHackingFancyFrameAction extends FreeAction {
    private final ElectricalDoor door;
    private final GameData gameData;

    public ShowDoorHackingFancyFrameAction(GameData gameData, Player forWhom, ElectricalDoor electricalDoor) {
        super("Examine Mechanism", gameData, forWhom);
        this.door = electricalDoor;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with the door mechanism";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        if (door.getDoorMechanism().isFancyFrameVacant()) {
            door.getDoorMechanism().setFancyFrameOccupied();
            FancyFrame ff = new DoorHackingFancyFrame(p, door, gameData);
            p.setFancyFrame(ff);
            p.setCharacter(new UsingGameObjectFancyFrameDecorator(p.getCharacter(), ff));
            p.refreshClientData();
        } else {
            gameData.getChat().serverInSay(
                    " The door mechanism is occupied right now, try again later.", p);
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}

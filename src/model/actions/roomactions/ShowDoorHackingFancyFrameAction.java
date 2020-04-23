package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.DoorHackingFancyFrame;
import model.fancyframe.FancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.map.doors.ElectricalDoor;

import java.util.List;

public class ShowDoorHackingFancyFrameAction extends Action {
    private final ElectricalDoor door;
    private final GameData gameData;

    public ShowDoorHackingFancyFrameAction(GameData gameData, Actor forWhom, ElectricalDoor electricalDoor) {
        super("Examine Door Mechanism", SensoryLevel.OPERATE_DEVICE);
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
        if (performingClient instanceof Player && door.getDoorMechanism().isFancyFrameVacant()) {
            door.getDoorMechanism().setFancyFrameOccupied();
            FancyFrame ff = new DoorHackingFancyFrame((Player)performingClient, door, gameData);
            ((Player) performingClient).setFancyFrame(ff);
            performingClient.setCharacter(new UsingGameObjectFancyFrameDecorator(performingClient.getCharacter(), ff));
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        } else {
            gameData.getChat().serverInSay(
                    " The door mechanism is occupied right now, try again later.", (Player)performingClient);

        }

    }
}

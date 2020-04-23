package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.map.StraightPowerCoord;
import model.map.doors.ElectricalDoor;
import util.HTMLText;

import java.awt.*;


public class DoorHackingFancyFrame extends FancyFrame {
    private final ElectricalDoor door;

    public DoorHackingFancyFrame(Player performingClient, ElectricalDoor door, GameData gameData) {
        super(performingClient.getFancyFrame());
        this.door = door;

        buildInterface(gameData, performingClient);
    }


    private void buildInterface(GameData gameData, Player player) {

        StringBuilder content = new StringBuilder();

        for (int i = 3; i > 0; --i) {
            content.append(HTMLText.makeImage(new StraightPowerCoord(Color.WHITE).getSprite(player)));
        }


        setData("Door Mechanism " + door.getNumber(), false,
                content.toString());
    }



    @Override
    public void leaveFancyFrame(GameData gameData, Player pl) {
        super.leaveFancyFrame(gameData, pl);
        door.getDoorMechanism().setFancyFrameVacant();
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildInterface(gameData, player);
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {

    }
}

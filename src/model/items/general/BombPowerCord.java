package model.items.general;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.doors.DoorMechanism;
import util.MyRandom;

import java.awt.*;

public class BombPowerCord extends model.map.doors.PowerCord {
    public BombPowerCord(Color color, int state) {
        super(color, state, null);
    }

    @Override
    public Action cut(Player player, GameData gameData) {
        setCut(true);
        return null;
    }

    @Override
    protected Action specificCutAction(Player player, GameData gameData) {
        // Should never be called
        return null;
    }

    @Override
    protected Action specificMendAction(Player player, GameData gameData) {
        // Should never be called
        return null;
    }

    @Override
    protected Action specificPulseAction(Player player, GameData gameData) {
        // Should never be called
        return null;
    }
}

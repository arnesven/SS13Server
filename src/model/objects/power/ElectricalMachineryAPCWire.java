package model.objects.power;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.doors.PowerCord;

import java.awt.*;

public class ElectricalMachineryAPCWire extends PowerCord {
    public ElectricalMachineryAPCWire() {
        super(PowerCord.randomPowerCordColor(), 1, null);
    }

    @Override
    protected Action specificCutAction(Player player, GameData gameData) {
        return null;
    }

    @Override
    protected Action specificMendAction(Player player, GameData gameData) {
        return null;
    }

    @Override
    protected Action specificPulseAction(Player player, GameData gameData) {
        return null;
    }
}

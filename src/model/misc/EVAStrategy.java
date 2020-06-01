package model.misc;

import model.GameData;
import model.Player;

import java.io.Serializable;

public abstract class EVAStrategy implements Serializable {

    private double rangeCubed;

    public EVAStrategy(double rangeCubed) {
        this.rangeCubed = rangeCubed;
    }

    public abstract boolean canMoveTo(Player player, GameData gameData, double x, double y, double z);

    protected boolean isWithinRange(double x, double y, double z) {
        return Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < rangeCubed;
    }


}

package model.misc;

import model.GameData;
import model.Player;
import model.map.Architecture;
import model.map.GameMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class EVAStrategy implements Serializable {

    private static final double minDY = -10;
    private static final double minDX = -10;
    private static final double maxDY = 10;
    private static final double maxDX = 10;
    private static final double maxDZ = 1;
    private static final double minDZ = -1;

    private double rangeCubed;

    public EVAStrategy(double rangeCubed) {
        this.rangeCubed = rangeCubed;
    }

    public abstract boolean canMoveTo(Player player, GameData gameData, double x, double y, double z, Architecture arch, Architecture above, Architecture below);

    protected boolean isWithinRange(double x, double y, double z) {
        return Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < rangeCubed;
    }

    public List<double[]> getMoveTargetPositions(GameData gameData, Player player) {
        Architecture arch = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME, (int)player.getCharacter().getSpacePosition().getZ());
        Architecture above = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME, (int)player.getCharacter().getSpacePosition().getZ() + 1);
        Architecture below = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME, (int)player.getCharacter().getSpacePosition().getZ() - 1);
        List<double[]> result = new ArrayList<>();
        for (double dz = minDZ; dz <= maxDZ; dz += 1) {
            for (double dy = minDY; dy <= maxDY; dy += 0.5) {
                for (double dx = minDX; dx <= maxDX; dx += 0.5) {
                    double x = player.getCharacter().getSpacePosition().getX() + dx;
                    double y = player.getCharacter().getSpacePosition().getY() + dy;
                    double z = player.getCharacter().getSpacePosition().getZ() + dz;
                    if (!(dx == 0.0 && dy == 0.0)) {
                        if (canMoveTo(player, gameData, x, y, z, arch, above, below)) {
                            result.add(new double[]{x, y, z});
                        }
                    }
                }
            }
        }
        return result;
    }
}

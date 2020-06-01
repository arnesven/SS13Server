package model.misc;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.rooms.Room;
import util.Logger;

public class AlongSurfacesEVAStrategy extends EVAStrategy {
//    private final Architecture arch;

    public AlongSurfacesEVAStrategy(GameData gameData) {
        super(50.0);
  //      this.arch = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME, (int)z);
    }

    @Override
    public boolean canMoveTo(Player player, GameData gameData, double x, double y, double z) {
        double dx = x - player.getCharacter().getSpacePosition().getX();
        double dy = y - player.getCharacter().getSpacePosition().getY();
        double dz = z - player.getCharacter().getSpacePosition().getZ();
        try {
            Room r = gameData.getMap().getRoomForCoordinates(x, y, z,
                    gameData.getMap().getLevelForRoom(player.getPosition()).getName());
            if (player.findMoveToAblePositions(gameData).contains(r) && isWithinRange(dx, dy, dz) &&
                    isNextToSurface(gameData, x, y, z)) {
                return true;
            }
        } catch (NoSuchThingException e) {
            if (isWithinRange(dx, dy, dz) && isNextToSurface(gameData, x, y, z)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNextToSurface(GameData gameData, double x, double y, double z) {
    //    if (arch.isAdjacentToRoom(x, y)) {
            return true;
    //    }

        // TODO: check over and under...

    //    return false;
    }
}

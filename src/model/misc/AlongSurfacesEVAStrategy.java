package model.misc;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.rooms.Room;

public class AlongSurfacesEVAStrategy extends EVAStrategy {

    public AlongSurfacesEVAStrategy() {
        super(50.0);
    }

    @Override
    public boolean canMoveTo(Player player, GameData gameData, double x, double y, double z, Architecture arch, Architecture above, Architecture below) {
        double dx = x - player.getCharacter().getSpacePosition().getX();
        double dy = y - player.getCharacter().getSpacePosition().getY();
        double dz = z - player.getCharacter().getSpacePosition().getZ();
        try {
            Room r = gameData.getMap().getRoomForCoordinates(x, y, z,
                    gameData.getMap().getLevelForRoom(player.getPosition()).getName());
            if (player.findMoveToAblePositions(gameData).contains(r) && isWithinRange(dx, dy, dz) &&
                    isNextToSurface(x, y, z, arch, above, below)) {
                return true;
            }
        } catch (NoSuchThingException e) {
            if (isWithinRange(dx, dy, dz) && isNextToSurface(x, y, z, arch, above, below)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNextToSurface(double x, double y, double z, Architecture arch, Architecture above, Architecture below) {
        if (arch.isAdjacentToRoom(x, y)) {
            return true;
        }

        if (below.isOnRoom(x, y) || above.isOnRoom(x, y)) {
            return true;
        }
        return false;
    }
}

package model.misc;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.rooms.Room;

public class FreeMoveEVAStrategy extends EVAStrategy {
    public FreeMoveEVAStrategy() {
        super(60.0);
    }

    @Override
    public boolean canMoveTo(Player player, GameData gameData, double x, double y, double z, Architecture arch, Architecture above, Architecture below) {
        double dx = x - player.getCharacter().getSpacePosition().getX();
        double dy = y - player.getCharacter().getSpacePosition().getY();
        double dz = z - player.getCharacter().getSpacePosition().getZ();
        try {
            Room r = gameData.getMap().getRoomForCoordinates(x, y, z,
                    gameData.getMap().getLevelForRoom(player.getPosition()).getName());
            if (player.findMoveToAblePositions(gameData).contains(r) && isWithinRange(dx, dy, dz)) {
                return true;
            }
        } catch (NoSuchThingException e) {
            if (isWithinRange(dx, dy, dz)) {
                return true;
            }
        }
        return false;
    }
}

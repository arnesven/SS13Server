package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.SpaceVision;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.SpacePosition;
import model.map.rooms.Room;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class InSpaceCharacterDecorator extends CharacterDecorator {
    public InSpaceCharacterDecorator(GameCharacter character, GameData gameData) {
        super(character, "In Space");
        character.setSpacePosition(new SpacePosition(character.getPosition()));
    }

    @Override
    public String getFullName() {
        double x = getSpacePosition().getX();
        double y = getSpacePosition().getY();
        double z = getSpacePosition().getZ();
        return super.getFullName() + String.format(" (In Space at %1.1f,%1.1f,%1.1f)", x, y, z);
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        if (!isDead()) {
            return new SpaceVision().getOverlaySprites((Player)getActor(), gameData);
        }
        return super.getOverlayStrings(player, gameData);
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        Room r = findBestApproxRoom(gameData);
        getActor().moveIntoRoom(r);
    }

    private Room findBestApproxRoom(GameData gameData) {
        for (Room r : gameData.getMap().getAllRoomsOnSameLevel(getPosition())) {
            Rectangle rect = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            Point2D.Double p = new Point2D.Double(getSpacePosition().getX(), getSpacePosition().getY());
            if (rect.contains(p) && r.getZ() == getSpacePosition().getZ()) {
                return r;
            }
        }

        try {
            return gameData.getMap().getSpaceRoomForLevel(gameData.getMap().getLevelForRoom(getActor().getPosition()).getName());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

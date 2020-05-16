package model.map.rooms;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.doors.Door;

import java.util.ArrayList;
import java.util.List;

public abstract class OrbitalPlanetRoom extends PlanetRoom {

    public OrbitalPlanetRoom(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, shortname, x, y, width, height, neighbors, doors);
    }

    protected int getOrbitSpriteSize() {
        return 96;
    }

    protected abstract int getOrbitSpriteRow();

    protected abstract int getOrbitSpriteCol();

    public Sprite getOrbitSprite() {
        return new Sprite("orbitplanet" + getOrbitSpriteCol() + ";" + getOrbitSpriteRow()+";" +getOrbitSpriteSize(),
                "planets.png", getOrbitSpriteCol(), getOrbitSpriteRow(), getOrbitSpriteSize(),
                getOrbitSpriteSize(), null);
    }

    @Override
    public boolean isPartOfStation() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean shouldBeAddedToMinimap() {
        return false;
    }

    @Override
    public void addPlayer(Player client) {
        super.addPlayer(client);
        client.setCharacter(new SeeOnlyThisRoomDecorator(client.getCharacter()));
    }

    @Override
    public void removePlayer(Player client) throws NoSuchThingException {
        super.removePlayer(client);
        if (client.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof SeeOnlyThisRoomDecorator)) {
            client.removeInstance((GameCharacter gc) -> gc instanceof SeeOnlyThisRoomDecorator);
        }
    }

    private class SeeOnlyThisRoomDecorator extends CharacterDecorator {
        public SeeOnlyThisRoomDecorator(GameCharacter character) {
            super(character, "SeeOnlyThisRoom");
        }

        @Override
        public List<Room> getVisibleMap(GameData gameData) {
            List<Room> list = new ArrayList<>();
            list.add(OrbitalPlanetRoom.this);
            return list;
        }

        @Override
        public List<Room> getMiniMapRooms(GameData gameData) {
            return getVisibleMap(gameData);
        }
    }
}

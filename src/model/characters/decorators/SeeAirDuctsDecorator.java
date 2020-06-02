package model.characters.decorators;

import model.GameData;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.AirDuctRoom;
import model.map.rooms.Room;
import model.objects.VentObject;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class SeeAirDuctsDecorator extends CharacterDecorator {
    private VentObject ventObject;

    public SeeAirDuctsDecorator(VentObject ventObject, GameCharacter character) {
        super(character, "SeeAirDuctsDecorator");
        this.ventObject = ventObject;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Crawling)";
    }

    @Override
    public List<Room> getExtraMoveToLocations(GameData gameData) {
        List<Room> list = new ArrayList<>();
        list.addAll(super.getExtraMoveToLocations(gameData));
        if (ventObject != null) {
            list.add(ventObject.getToRoom());
        }
        for (GameObject obj : getPosition().getObjects()) {
            if (obj instanceof VentObject && (((VentObject) obj).isOpen() || super.getSize() == SMALL_SIZE)) {
                list.add(((VentObject) obj).getToRoom());
            }
        }
        return list;
    }

    @Override
    public List<Room> getVisibleMap(GameData gameData) {
        List<Room> result = super.getVisibleMap(gameData);
        try {
            for (Room r : gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(getPosition()).getName())) {
                if (r instanceof AirDuctRoom) {
                    result.add(r);
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        } ;
        return result;
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        if (!(getPosition() instanceof AirDuctRoom)) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}

package model.events.ambient;

import model.map.rooms.Room;

public interface PressureManipulator {
    double handlePressure(Room r, double currentPressure);
}

package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.RepairAction;
import model.map.doors.ElectricalDoor;

public class RepairDoorAction extends RepairAction {
    public RepairDoorAction(GameData gameData, Actor forWhom, ElectricalDoor d) {
        super(forWhom);
        setName("Repair " + d.getName());
        addTarget(d.getBreakableObject());
        stripAllTargetsBut(d.getBreakableObject());
    }
}

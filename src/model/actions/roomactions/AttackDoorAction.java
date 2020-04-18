package model.actions.roomactions;

import model.Actor;
import model.actions.general.AttackAction;
import model.map.doors.Door;

public class AttackDoorAction extends AttackAction {
    public AttackDoorAction(Actor forWhom, Door d) {
        super(forWhom);
        setName("Attack " + d.getName());
    }

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }
}

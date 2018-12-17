package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.SqueakAction;
import model.actions.general.Action;

public class SquakingBehavior extends SpontaneousAct {

    public SquakingBehavior() {
        super(0.3, new SqueakAction());
    }
}

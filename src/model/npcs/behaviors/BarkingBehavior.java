package model.npcs.behaviors;

import model.actions.characteractions.BarkingAction;
import model.actions.general.Action;
import model.actions.general.SpeechAction;

/**
 * Created by erini02 on 03/09/16.
 */
public class BarkingBehavior extends SpontaneousAct {
    public BarkingBehavior() {
        super(0.75, new BarkingAction());
    }
}

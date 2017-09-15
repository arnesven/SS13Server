package model.modes.goals;

import model.items.Brain;
import model.items.general.GameItem;

/**
 * Created by erini02 on 15/09/17.
 */
public class HaveABrain extends HaveItemGoal {
    public HaveABrain() {
        super(new Brain());
    }
}

package model.modes.goals;

import model.items.foods.Orange;
import model.items.general.GameItem;

/**
 * Created by erini02 on 15/09/17.
 */
public class HaveAnOrange extends HaveItemGoal {
    public HaveAnOrange() {
        super(new Orange(null));
    }
}

package model.modes.goals;

import model.items.general.GameItem;
import model.items.weapons.Crowbar;

/**
 * Created by erini02 on 15/09/17.
 */
public class HaveACrowbar extends HaveItemGoal {
    public HaveACrowbar() {
        super(new Crowbar());
    }
}

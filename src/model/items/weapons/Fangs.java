package model.items.weapons;

import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Fangs extends Weapon {
    public Fangs() {
        super("Fangs", 0.75, 0.5, false, 0.0, 0);
    }

    @Override
    public GameItem clone() {
        return new Fangs();
    }
}

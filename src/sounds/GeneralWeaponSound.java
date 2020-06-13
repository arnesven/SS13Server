package sounds;

import util.MyRandom;

public class GeneralWeaponSound extends Sound {
    public GeneralWeaponSound() {
        super("genhit" + (MyRandom.nextInt(4)+1));
    }
}

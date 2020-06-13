package sounds;

import util.MyRandom;

public class AlienHissSound extends Sound {
    public AlienHissSound() {
        super("hiss" + (MyRandom.nextInt(6)+1));
    }
}

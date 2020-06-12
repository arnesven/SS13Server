package sounds;

import util.MyRandom;

public class FartSound extends Sound {
    public FartSound() {
        super("fart" + (MyRandom.nextInt(2)+1));
    }
}

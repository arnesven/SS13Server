package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class NanotrasenDummy extends CosmicMonolith {
    private final Boolean smooth;
    private final Boolean radiation;
    private final Boolean hollow;
    private final Boolean sound;
    private final Boolean current;
    private final Boolean markings;
    private final Boolean light;
    private final Boolean pressure;

    public NanotrasenDummy() {
        super("Nanotrasen Dummy");
        light = MyRandom.nextBoolean();
        pressure = MyRandom.nextBoolean();
        smooth = MyRandom.nextBoolean();
        radiation = MyRandom.nextBoolean();
        hollow = MyRandom.nextBoolean();
        sound = MyRandom.nextBoolean();
        current = MyRandom.nextBoolean();
        markings = MyRandom.nextBoolean();
    }

    @Override
    public boolean reactsToLightHeat() {
        return false;
    }

    @Override
    public boolean reactsToFlames() {
        return false;
    }

    @Override
    public boolean reactsToPressure() {
        return false;
    }

    @Override
    public boolean isTouchSmooth() {
        return smooth;
    }

    @Override
    public boolean doesEmitRadiation() {
        return radiation;
    }

    @Override
    public boolean isHollow() {
        return hollow;
    }

    @Override
    public boolean doesReactToSound() {
        return sound;
    }

    @Override
    public boolean doesConductCurrent() {
        return current;
    }

    @Override
    public boolean hasMarkings() {
        return markings;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return true;
    }

    @Override
    public String getNotesText() {
        return "If I'm honest, I can't rule out that the monolith could actually be just a dummy sent here by Nanotrasen" +
                " to test my skills as a scientist and to make sure we are actually working. I've heard of them pulling" +
                " such stunts on other facilities. If this is the case then it could have just about any kind of properties " +
                " except that they probably wouldn't send anything flammable, because they're fanatical about anti-fire safety," +
                " nor would it be radioactive. And it would probably be made out of something cheap that melts or breaks " +
                " when you pour acid on it.";
    }

    @Override
    public String getEnding() {
        return " it is an insult to me and my team. Honestly, why do we have to put up with these kinds of shenanigans?!";
    }

    @Override
    public GameItem clone() {
        return new NanotrasenDummy();
    }
}

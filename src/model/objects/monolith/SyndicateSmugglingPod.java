package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class SyndicateSmugglingPod extends CosmicMonolith {
    private final Boolean radiation;

    public SyndicateSmugglingPod() {
        super("Syndicate Smuggling Pod");
        radiation = MyRandom.nextBoolean();
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
        return true;
    }

    @Override
    public boolean doesEmitRadiation() {
        return radiation;
    }

    @Override
    public boolean isHollow() {
        return true;
    }

    @Override
    public boolean doesReactToSound() {
        return false;
    }

    @Override
    public boolean doesConductCurrent() {
        return true;
    }

    @Override
    public boolean hasMarkings() {
        return true;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return false;
    }

    @Override
    public String getNotesText() {
        return "Over the years, Nanotrasen has intercepted a number of \"fake monoliths\" which turned out to be smuggling" +
                " vessels for the Syndicate. Such pods could quickly be jettisoned by" +
                " a smuggler should a patrol come around, and being smooth and opaque, most scanners would overlook such" +
                " object or mistake them for regular debris or small asteroids. The feds who investigated the intercepted" +
                " pods described them as \"unresponsive to light/heat, flame, pressure and sonic waves, but obviously hollow and with" +
                " internal wiring which reacts to electrical pulses. All such pods had markings a long the sides, which" +
                " are thought to be an encoded description of the pods contents.\"";
    }

    @Override
    public String getEnding() {
        return " its contents is still unknown and Syndicate operatives have been known to infiltrate facilities such as these" +
                " to recover items which are of interest to them";
    }

    @Override
    public GameItem clone() {
        return new SyndicateSmugglingPod();
    }
}

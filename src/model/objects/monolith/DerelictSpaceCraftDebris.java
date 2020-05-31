package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class DerelictSpaceCraftDebris extends CosmicMonolith {
    private boolean hollow;
    private boolean markings;

    public DerelictSpaceCraftDebris() {
        super("Derelict Space Craft Debris");
        this.hollow = MyRandom.nextBoolean();
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
        return true;
    }

    @Override
    public boolean doesEmitRadiation() {
        return false;
    }

    @Override
    public boolean isHollow() {
        return hollow;
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
        return markings;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return false;
    }

    @Override
    public String getNamePlural() {
        return getBaseName();
    }

    @Override
    public String getNotesText() {
        return "Should the monolith turn out to be just some old piece of smashed up space craft one can expect a metallic" +
                " or pseudometallic electrically conducting material forming a smooth outer shell with or without markings." +
                " Such objects normally do not react to light/heat, nor to open flame or pressure. Radioactivity should be" +
                " minimal to none. Sound waves should not affect it nor should it react noticeably to corrosive agents.";
    }

    @Override
    public String getEnding() {
        return " it is of no further interest to us and should be disposed of or recycled according to standard Nanotrasen" +
                " procedure";
    }

    @Override
    public GameItem clone() {
        return new DerelictSpaceCraftDebris();
    }
}

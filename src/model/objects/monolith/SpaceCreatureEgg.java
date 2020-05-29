package model.objects.monolith;

import model.items.CosmicArtifact;
import model.items.general.GameItem;
import util.MyRandom;

public class SpaceCreatureEgg extends CosmicArtifact {

    private boolean touch;
    private boolean hollow;

    public SpaceCreatureEgg() {
        super("Space Creature Egg");
        touch = MyRandom.nextBoolean();
        hollow = MyRandom.nextBoolean();
    }

    @Override
    public GameItem clone() {
        return new SpaceCreatureEgg();
    }

    @Override
    public String getNotesText() {
        return "Eggs of extraterrestrials found in space are often, in spite of inhospitable locations, in good condition." +
                " They commonly react to heat, light and fire and also when pressure or sound waves are applied. They do not emit radiation" +
                " nor do they have any markings. Space creature eggs have been found to conduct electrical current.<br/>" +
                " Both smooth and rough surface eggs have been found, depending on species. " +
                " furthermore it is not always easy to tell whether a space creature egg is hollow or not." +
                " When a corrosive compound is applied, a pungent smell often emerges.";
    }

    @Override
    public boolean reactsToLightHeat() {
        return true;
    }

    @Override
    public boolean reactsToFlames() {
        return true;
    }

    @Override
    public boolean reactsToPressure() {
        return true;
    }

    @Override
    public boolean isTouchSmooth() {
        return this.touch;
    }

    @Override
    public boolean doesEmitRadiation() {
        return false;
    }

    @Override
    public boolean isHollow() {
        return this.hollow;
    }

    @Override
    public boolean doesReactToSound() {
        return true;
    }

    @Override
    public boolean doesConductCurrent() {
        return true;
    }

    @Override
    public boolean hasMarkings() {
        return false;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return true;
    }

    @Override
    public String getEnding() {
        return " the state of the egg (dormant, fertilized or not) is not known " +
                "and may hatch or spread infectious disease. We would like to avoid another incident";
    }
}

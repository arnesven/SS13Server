package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class FossilizedSpaceCreature extends CosmicMonolith {
    public FossilizedSpaceCreature() {
        super("Fossilized Space Creature");
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
        return false;
    }

    @Override
    public boolean doesEmitRadiation() {
        return false;
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
        return false;
    }

    @Override
    public boolean hasMarkings() {
        return false;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return false;
    }

    @Override
    public String getNotesText() {
        return "Turned into stone by time and the elements, ancient alien creatures are preserved in rock from" +
                " time to time. Like other rocky objects, they do not react to many types of exposure (e.g. light/heat," +
                " flames, pressure, sound, corrosive chemicals or electrical current, nor are they typically radioactive. They are most" +
                " often distinguishable by their shape, rough surface and that they often have hollow cavities were " +
                " organs of the creature once were.";
    }

    @Override
    public String getEnding() {
        return "it may be valuable to a collector and could generate some extra income for the company";
    }

    @Override
    public GameItem clone() {
        return new FossilizedSpaceCreature();
    }
}

package model.objects.monolith;

import model.items.CosmicArtifact;
import model.items.general.GameItem;

public class RadioactiveRock extends CosmicArtifact {
    public RadioactiveRock() {
        super("Radioactive Rock");
    }

    @Override
    public GameItem clone() {
        return new RadioactiveRock();
    }

    @Override
    public String getNotesText() {
        return "The universe is filled with these little warm lumps. Used long ago on Earth as sources of energy, they nowadays mostly serve as fodder" +
                " for the asteroid sweepers. Anybody who has taken an introductory course to space geology knows that radioactive rocks" +
                " respond neither to heat/light, flames, pressure, sound waves nor to electrical current. They are rarely hollow and" +
                " have a rough exterior surface without markings.";
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
        return true;
    }

    @Override
    public boolean isHollow() {
        return false;
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
    public String getEnding() {
        return " the radiation may potentially be harmful for the station's crew";
    }
}

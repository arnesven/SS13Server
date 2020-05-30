package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class AlienRosettaStone extends CosmicMonolith {

    public AlienRosettaStone() {
        super("Alien Rosetta Stone");
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
        return true;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return false;
    }

    @Override
    public String getNotesText() {
        return "Left behind by benevolent ancient races these types of artifacts have helped scientist unravel a multitude" +
                " of extinct alien languages. Named after the original Rosetta Stone of old Earth, these cosmic equivalents" +
                " are most often etched in a very hard and smooth, solid rock. Such rocks react neither to light/heat, flames," +
                " pressure, sound nor corrosive chemicals and very rarely contain electrically conducting elements or" +
                " radioactive isotopes.";
    }

    @Override
    public String getEnding() {
        return "it is of very high scientific value and should be researched further by xenolinguistics";
    }


    @Override
    public GameItem clone() {
        return new AlienRosettaStone();
    }
}

package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class AlienSarcophagus extends CosmicMonolith {
    public AlienSarcophagus() {
        super("Alien Sarcophagus");
    }

    @Override
    public boolean reactsToLightHeat() {
        return true;
    }

    @Override
    public boolean reactsToFlames() {
        return false;
    }

    @Override
    public boolean reactsToPressure() {
        return true;
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
        return "Apparently, most space-faring beings do at some point adopt the custom of \"space burials\"." +
                " A number of sarcophagi have been found, floating in uncharted asteroid fields, or in orbit around" +
                " remote rogue planets. The contents have all been declared to be remains of (presumed) sentient alien" +
                " species. Such pods often reflect light/heat very well to protect from intense solar exposure and are" +
                " often completely unharmed by actual fire or abrasive chemical agents. They can rarely withstand high pressure as they are hollow" +
                " and normally made from a metallic alloy. One can expect a smooth surface likely marked with letters," +
                " runes or pictographs to spiritually safeguard the vessel on its voyage through the stars. Radiation"  +
                " and sound waves normally have no effect, nor have scientist every uncovered any that send out any such signals. ";
    }

    @Override
    public String getEnding() {
        return "it must be researched further and ultimately belongs in a museum";
    }

    @Override
    public GameItem clone() {
        return new AlienSarcophagus();
    }

    @Override
    public String getNamePlural() {
        return "Alien Sarcophagi";
    }
}

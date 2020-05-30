package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class CompactedSpaceTrash extends CosmicMonolith {
    public CompactedSpaceTrash() {
        super("Compacted Space Trash");
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
        return false;
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
    public String getNotesText() {
        return "Nowadays most space trash is transported to Sellberg's Point or other such recycling planets where it" +
                " is properly taken care of. But it was not long ago that each and every star liner and space station had" +
                " its own trash compactor which would smash waste into tiny cubes and then promptly release them into space." +
                " Many a treasure hunter have been disappointed when what was thought to be a rare cosmic find, turned out to" +
                " be yesterday's discarded pizza boxes.<br/>" +
                " Space trash usually heats up rapidly when exposed to heat rays, burns well when exposed to fire and comes apart" +
                " or is misshaped when pressure is applied. The surface is often rough to the touch and the inside is usually" +
                " quite solid. Sound waves have no effect on such objects and no radiation can usually be measured" +
                " (unless in extreme cases). The cubes conduct electricity fairly well. Any text, pictures or markings" +
                " are normally completely erased in the compacting process. It is not advised to apply corrosive chemicals" +
                " to space trash as the fumes are sometimes toxic and always unpleasant.";
    }

    @Override
    public String getEnding() {
        return "it is rather smelly and we have enough trash around here already";
    }

    @Override
    public GameItem clone() {
        return new CompactedSpaceTrash();
    }

    @Override
    public String getNamePlural() {
        return getBaseName();
    }
}

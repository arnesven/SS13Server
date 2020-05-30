package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class CommonSpaceRock extends CosmicMonolith {
    public CommonSpaceRock() {
        super("Common Space Rock");
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
    public String getNotesText() {
        return "The universe is full of them. It's quite possible that somebody picked one up and erroneously (or maliciously)" +
                " declared it to be \"a strange object\" worthy of research.<br/>If this is the case I should keep in mind" +
                " the qualities of common space rocks, which I have outlined on the previous page, just sans the radioactivity.";
    }

    @Override
    public String getEnding() {
        return " it is completely useless and obviously was delivered here by mistake or as a rouse. I must say, if" +
                " this is some kind of a joke it is in very bad taste.";
    }

    @Override
    public GameItem clone() {
        return new CommonSpaceRock();
    }
}

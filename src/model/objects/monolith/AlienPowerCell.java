package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class AlienPowerCell extends CosmicMonolith {
    private boolean hollow;

    public AlienPowerCell() {
        super("Alien Power Cell");
        hollow = MyRandom.nextBoolean();
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
        return false;
    }

    @Override
    public boolean isTouchSmooth() {
        return true;
    }

    @Override
    public boolean doesEmitRadiation() {
        return true;
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
        return false;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        return false;
    }

    @Override
    public String getNotesText() {
        return "\"You can tell a lot about an ancient civilization by looking through its trash.\" A famous quote by" +
                " the Imperial Xenobiologist Fredrique von Frippe.<br/>Humans are not the only race to carelessly jettison" +
                " whatever materials are no longer needed on our planets, space stations and star liners. Alien power" +
                " cells are quite prevalent are well researched.<br/>" +
                " These objects respond to light/heat rays, emit fairly strong doses of radiation and conduct electrical" +
                " current. Flames, pressure, sound or corrosive agents normally have no effect. They are often free from" +
                " markings and have quite the smooth surface. Both hollow (presumed exhausted) and solid ones have been" +
                " found but scientist have not yet been able to tap whatever energy is left within them." ;
    }

    @Override
    public String getEnding() {
        return hollow?"it is most likely exhausted and is a potential biohazard":"it still has some content and may be" +
                " explosive or otherwise harmful for the local personnel";
    }

    @Override
    public GameItem clone() {
        return new AlienPowerCell();
    }
}

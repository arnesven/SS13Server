package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class AlienNavigationalBuoy extends CosmicMonolith {

    private boolean hollow;

    public AlienNavigationalBuoy() {
        super("Alien Navigational Buoy");
        hollow = MyRandom.nextBoolean();
    }

    @Override
    public GameItem clone() {
        return new AlienNavigationalBuoy();
    }

    @Override
    public String getNotesText() {
        return "Remnants of an old navigational grid, these artifact of a lost race can appear floating in space." +
                " Of the few that have been found even fewer have been properly researched since most have been locked away" +
                " in governmental warehouses or stolen by fanatic collectors.</br></br>" +
                " However, previous experiments show that navigational buoys do not react to heat/light, flames nor pressure but" +
                " do in fact emit radiation to guide the ancient ships among the stars. They are normally smooth to the touch and" +
                " conduct electrical current.";
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
        return this.hollow;
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
    public String getEnding() {
        return "objects of this kind tend to attract unwanted attention from treasure hunters and black marketeers alike";
    }
}

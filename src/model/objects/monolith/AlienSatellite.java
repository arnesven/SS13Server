package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class AlienSatellite extends CosmicMonolith {
    private boolean hollow;
    private boolean markings;

    public AlienSatellite() {
        super("Alien Satellite");
        hollow = MyRandom.nextBoolean();
        markings = MyRandom.nextBoolean();
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
    public String getNotesText() {
        return "These left behind beacons of an old galactic communications system can be found way out in interstellar" +
                " space, usually quite well preserved as they are normally built to withstand fire, pressure, corrosive chemicals and sonic waves." +
                " Some may even be functional to a degree. They typically have" +
                " some form of photovoltaic cell which reacts to light or heat and charges the satellites' systems. Almost all" +
                " such satellites rely on electrical current to drive the complex systems which are harboured under a smooth" +
                " outer shell. Some such satellites have been found to have markings, but not all. Whether the markings indicate the origin or ownership" +
                " of the device, or if they are meant as messages or warnings to passer-bys is still unknown however.";
    }

    @Override
    public String getEnding() {
        return " it might still be functional and attract unwanted attention to this facility, should somebody be listening that is";
    }

    @Override
    public GameItem clone() {
        return new AlienSatellite();
    }
}

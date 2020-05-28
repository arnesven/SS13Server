package model.objects.monolith;

import model.items.CosmicArtifact;
import model.items.general.GameItem;

public class AlienNavigationalBuoy extends CosmicArtifact {

    public AlienNavigationalBuoy() {
        super("Alien Navigational Buoy");
    }

    @Override
    public GameItem clone() {
        return new AlienNavigationalBuoy();
    }
}

package model.objects.monolith;

import model.items.CosmicArtifact;
import model.items.general.GameItem;

public class SpaceCreatureEgg extends CosmicArtifact {

    public SpaceCreatureEgg() {
        super("Space Creature Egg");
    }

    @Override
    public GameItem clone() {
        return new SpaceCreatureEgg();
    }
}

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
}

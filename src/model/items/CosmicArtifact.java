package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.items.general.GameItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public class CosmicArtifact extends GameItem {
    private final Sprite sprite;

    public CosmicArtifact() {
        super("Cosmic Artifact", 1.0, false);
        this.sprite = MyRandom.sample(spritelist());
    }

    private static List<Sprite> spritelist() {
        List<Sprite> sprs = new ArrayList<>();
        for (int i = 17; i < 29; ++i) {
            sprs.add(new Sprite("cosmicartifact", "weapons2.png", i, 24, 32, 32));
        }
        sprs.add(new Sprite("cosmicartifact", "weapons2.png", 7, 24, 32, 32));
        sprs.add(new Sprite("cosmicartifact", "weapons2.png", 11, 24, 32, 32));
        return sprs;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    @Override
    public GameItem clone() {
        return new CosmicArtifact();
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {

    }
}

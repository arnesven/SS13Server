package model.items;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.MindControlAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.objects.monolith.AlienNavigationalBuoy;
import model.objects.monolith.RadioactiveRock;
import model.objects.monolith.SpaceCreatureEgg;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public abstract class CosmicArtifact extends GameItem {
    private final Sprite sprite;

    public CosmicArtifact(String name) {
        super(name, 1.0, false, 1000);
        this.sprite = MyRandom.sample(spritelist(this));
        Logger.log(Logger.INTERESTING, "Cosmic Artifact Sprite is " + getSprite(null).getName());
    }

    public abstract String getNotesText();

    private static List<Sprite> spritelist(SpriteObject obj) {
        List<Sprite> sprs = new ArrayList<>();
        for (int i = 17; i < 29; ++i) {
            sprs.add(new Sprite("cosmicartifact"+i, "weapons2.png", i, 24, 32, 32, obj));
        }
        sprs.add(new Sprite("cosmicartifacta", "weapons2.png", 7, 24, 32, 32, obj));
        sprs.add(new Sprite("cosmicartifactb", "weapons2.png", 11, 24, 32, 32, obj));
        return sprs;
    }

    public static CosmicArtifact getRandomArtifact() {
        return MyRandom.sample(getAllTypes());
    }

    public static List<CosmicArtifact> getAllTypes() {
        List<CosmicArtifact> result = new ArrayList<>();
        result.add(new SpaceCreatureEgg());
        result.add(new AlienNavigationalBuoy());
        result.add(new RadioactiveRock());
        return result;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    public String getNamePlural() {
        return getBaseName() + "s";
    }

    public abstract boolean reactsToLightHeat();
    public abstract boolean reactsToFlames();
    public abstract boolean reactsToPressure();
    public abstract boolean isTouchSmooth();
    public abstract boolean doesEmitRadiation();
    public abstract boolean isHollow();
    public abstract boolean doesReactToSound();
    public abstract boolean doesConductCurrent();
    public abstract boolean hasMarkings();
    public abstract boolean smellsWhenCorrosiveApplied();

    public abstract String getEnding();
}

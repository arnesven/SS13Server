package model.items;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.items.general.GameItem;
import model.objects.monolith.*;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public abstract class CosmicMonolith extends GameItem {
    private final Sprite sprite;

    public CosmicMonolith(String name) {
        super(name, 1.0, false, 1000);
        this.sprite = MyRandom.sample(spritelist(this));
        Logger.log(Logger.INTERESTING, "Cosmic Monolith Sprite is " + getSprite(null).getName());
    }


    private static List<Sprite> spritelist(SpriteObject obj) {
        List<Sprite> sprs = new ArrayList<>();
        for (int i = 17; i < 29; ++i) {
            sprs.add(new Sprite("cosmicmonolith"+i, "weapons2.png", i, 24, 32, 32, obj));
        }
        sprs.add(new Sprite("cosmicmonolitha", "weapons2.png", 7, 24, 32, 32, obj));
        sprs.add(new Sprite("cosmicmonolithb", "weapons2.png", 11, 24, 32, 32, obj));
        return sprs;
    }

    public static CosmicMonolith getRandomMonolith() {
        return MyRandom.sample(getAllTypes());
    }

    public static List<CosmicMonolith> getAllTypes() {
        List<CosmicMonolith> result = new ArrayList<>();
        result.add(new AlienNavigationalBuoy());
        result.add(new AlienRosettaStone());
        result.add(new AlienSarcophagus());
        result.add(new AlienPowerCell());
        result.add(new AlienSatellite());
        result.add(new CompactedSpaceTrash());
        result.add(new DoomsdayMachine());
        result.add(new FossilizedSpaceCreature());
        result.add(new RadioactiveRock());
        result.add(new CommonSpaceRock());
        result.add(new NanotrasenDummy());
        result.add(new DerelictSpaceCraftDebris());
        result.add(new RareElementsRock());
        result.add(new SpaceCreatureEgg());
        result.add(new SpaceCreatureExcrement());
        result.add(new SyndicateSmugglingPod());
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

    public abstract String getNotesText();
    public abstract String getEnding();
}

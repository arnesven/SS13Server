package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;

public class RareElementsRock extends CosmicMonolith {
    public RareElementsRock() {
        super("Rare Elements Rock");
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
        return "Being also a mining vessel SS13 sees its fair share of rare mineral deposits. Normally they're smashed" +
                " up over at the mining station and mined for ore shards. Such boulders display most features of other" +
                " rocks (see previous entries) but tend to also conduct electrical current well.";
    }

    @Override
    public String getEnding() {
        return "it should be mined for its materials which may be considerably valuable. Furthermore, it would be prudent to reward" +
                " those who discerned this find for what it is with a bonus of some kind";
    }

    @Override
    public GameItem clone() {
        return new RareElementsRock();
    }
}

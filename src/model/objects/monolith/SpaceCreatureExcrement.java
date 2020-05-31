package model.objects.monolith;

import model.items.CosmicMonolith;
import model.items.general.GameItem;
import util.MyRandom;

public class SpaceCreatureExcrement extends CosmicMonolith {
    private final Boolean radiation;
    private boolean smooth;

    public SpaceCreatureExcrement() {
        super("Space Creature Excrement");
        smooth = MyRandom.nextBoolean();
        radiation = MyRandom.nextBoolean();
    }

    @Override
    public boolean reactsToLightHeat() {
        return false;
    }

    @Override
    public boolean reactsToFlames() {
        return true;
    }

    @Override
    public boolean reactsToPressure() {
        return true;
    }

    @Override
    public boolean isTouchSmooth() {
        return smooth;
    }

    @Override
    public boolean doesEmitRadiation() {
        return radiation;
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
        return false;
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
        return "Big creatures leave big droppings behind. So it is also with many creatures living in space. Properties" +
                " of such objects vary but normally you will find that they melt or burn upon contact with heat waves or" +
                " actual flames and will crumble or flatten when pressure is applied. Both rough and smooth surfaced" +
                " droppings have been found, and in some cases even radioactive ones! Hallow cavities are unlikely " +
                " and such objects normally do not react to sound waves or conduct electrical current. Any contact with" +
                " abrasive or oxidizing chemicals usually result in unpleasant and even revolting odour.";
    }

    @Override
    public String getEnding() {
        return " it's just a pile of poop and is of no further interest";
    }

    @Override
    public GameItem clone() {
        return new SpaceCreatureExcrement();
    }
}

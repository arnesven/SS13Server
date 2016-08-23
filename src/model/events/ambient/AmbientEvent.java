package model.events.ambient;

import model.events.Event;

/**
 * Created by erini02 on 03/05/16.
 */
public abstract class AmbientEvent extends Event {

    private double prob;

    public AmbientEvent() {
        this.prob = getStaticProbability();
    }

    protected abstract double getStaticProbability();

    @Override
    public final double getProbability() {
        return prob;
    }

    @Override
    public final void setProbability(double v) {
        this.prob = v;
    }
}

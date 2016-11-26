package model.events.ambient;

import model.events.Event;
import util.Logger;

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

    public static double everyNGames(int n) {
        double d = 1 - Math.pow(1 - 1.0/n, (1.0/20.0));
        Logger.log(Logger.INTERESTING, "Some event hade prob " + d);
        return d;
    }
}

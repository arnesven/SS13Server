package model.modes.goals;


import model.Actor;
import model.modes.objectives.Objective;

/**
 * Created by erini02 on 03/12/16.
 */
public abstract class PersonalGoal implements Objective {

    private Actor belongsTo;

    public Actor getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Actor belongingTo) {
        this.belongsTo = belongingTo;
    }
}

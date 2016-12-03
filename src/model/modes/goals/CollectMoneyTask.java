package model.modes.goals;

import model.Actor;
import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;

/**
 * Created by erini02 on 03/12/16.
 */
public class CollectMoneyTask extends PersonalGoal {
    public CollectMoneyTask(Actor belongsTo) {
        super(belongsTo);
    }

    @Override
    public String getText() {
        return "Collect $$ 1000";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            MoneyStack m = MoneyStack.getActorsMoney(getBelongsTo());
            return m.getAmount() >= 1000;
        } catch (NoSuchThingException e) {

        }
        return false;
    }
}

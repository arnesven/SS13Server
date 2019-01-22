package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Target;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MoneyStack;

public class FlyingCredit extends Weapon implements PiercingWeapon {
    public FlyingCredit() {
        super("Flying Credit Chip", 0.5, 1.0, false, 0.0, false, 0);
    }

    @Override
    public GameItem clone() {
        return new FlyingCredit();
    }

    @Override
    public void usedOnBy(Target target, Actor performingClient,
                         final GameData gameData) {
       super.usedOnBy(target, performingClient, gameData);
       if (target instanceof Actor) {
           try {
               MoneyStack st = MoneyStack.getActorsMoney((Actor)target);
               st.addTo(1);
           } catch (NoSuchThingException e) {
               e.printStackTrace();
           }
       }
    }


    @Override
    protected void checkOnlyMissHazard(Actor performingClient, GameData gameData) {
        super.checkOnlyMissHazard(performingClient, gameData);
        performingClient.getPosition().addItem(new MoneyStack(1));
    }
}

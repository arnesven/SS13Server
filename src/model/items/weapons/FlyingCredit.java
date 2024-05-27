package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.events.animation.AnimatedSprite;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MoneyStack;

import java.awt.*;

public class FlyingCredit extends Weapon implements PiercingWeapon {
    public FlyingCredit() {
        super("Flying Credit Chip", 0.5, 1.0, false, 0.0, false, 0);
    }

    @Override
    public GameItem clone() {
        return new FlyingCredit();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
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
    protected void checkOnlyMissHazard(Actor performingClient, GameData gameData, Target target) {
        super.checkOnlyMissHazard(performingClient, gameData, target);
        performingClient.getPosition().addItem(new MoneyStack(1));
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite sp = new AnimatedSprite("flyingcreditchip", "laser.png",
                0, 4, 32, 32, null, 7, false);
        sp.setColor(Color.MAGENTA);
        return sp;
    }
}

package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;

/**
 * Created by erini02 on 03/05/16.
 */
public class ZombieDecorator extends CharacterDecorator {
    public ZombieDecorator(GameCharacter character) {
        super(character, "zombified");
    }

   @Override
    public Sprite getSprite(Actor whosAsking) {
       if (isDead()) {
           return new Sprite("gore", "human.png", 137, this);
       }
        return new Sprite("zombie", "human.png", 133, this);
    }

    @Override
    public String getPublicName() {
        if (isDead()) {
            return "Husk";
        }
        return "Zombie";
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof RadiationDamage ||
                damager instanceof ColdDamage ||
                damager instanceof AsphyxiationDamage) {
            return;
        }
        super.beExposedTo(something, damager);
    }
}

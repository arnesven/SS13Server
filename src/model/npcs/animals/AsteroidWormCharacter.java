package model.npcs.animals;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.weapons.Weapon;

public class AsteroidWormCharacter extends AnimalCharacter {
    public AsteroidWormCharacter(int id) {
        super("Asteroid Worm", id, -4.0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = new Sprite("asteroidworm", "synthetic.png", 15, 26, getActor());
        if (isDead()) {
            sp.setRotation(90);
        }
        return sp;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if ( damager instanceof AsphyxiationDamage || damager instanceof RadiationDamage || damager instanceof ColdDamage) {
            return;
        } else {
            super.beExposedTo(something, damager);
        }
    }

    @Override
    public int getSize() {
        return LARGE_SIZE;
    }

    @Override
    public GameCharacter clone() {
        return new AsteroidWormCharacter(getStartingRoom());
    }

        @Override
    public Weapon getDefaultWeapon() {
        return Weapon.TUSKS;
    }
}

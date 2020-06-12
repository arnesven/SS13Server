package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;
import model.events.damage.ScreamingAction;
import model.items.suits.Equipment;
import model.items.weapons.*;
import model.objects.general.BloodyMess;
import sounds.HumanSoundSet;
import util.MyRandom;

public abstract class HumanCharacter extends GameCharacter {
	
	public HumanCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
		super.setSoundSet(new HumanSoundSet(this));
	}

	public String getPublicName() {
		String res = getBaseName();
		if (getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) == null) {
			res = "Naked " + getGender() ;
		}
		return res;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = super.getSprite(whosAsking);
        if (isDead()) {
            sp.setRotation(90.0);
        }
        return sp;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        double hp = getHealth();
	    super.beExposedTo(something, damager);

	    if (damager instanceof ExplosiveDamage) {
            if (hp > getHealth() && MyRandom.nextDouble() < BloodyMess.SPAWN_CHANCE) {
                getPosition().addObject(new BloodyMess(getPosition()));
            }
        }

    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
	    double hp = getHealth();
        boolean succ = super.beAttackedBy(performingClient, weapon, gameData);
        if (weapon instanceof SlashingWeapon || weapon instanceof BludgeoningWeapon || weapon instanceof PiercingWeapon) {
            if (succ && hp > getHealth()  && MyRandom.nextDouble() < BloodyMess.SPAWN_CHANCE) {
                performingClient.getPosition().addObject(new BloodyMess(performingClient.getPosition()));
            }
        }

        return succ;
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return !isDead();
    }

}

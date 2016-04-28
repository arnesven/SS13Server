package model.characters.decorators;

import model.Actor;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;

public class SpaceProtection extends CharacterDecorator {

	public SpaceProtection(GameCharacter chara) {
		super(chara, "Space protection");
	}
	
	@Override
	public String getPublicName() {
		String res = "Space suit";
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}

//    @Override
//    public Sprite getSprite(Actor whosAsking) {
//        Sprite mult;
//        ArrayList<Sprite> ontops = new ArrayList<>();
//       mult = new Sprite("spaceprotection", "naked.png", 0, 0, 32, 32, ontops);
//        SpriteManager.testSprite(mult);
//        return mult;
//    }

    @Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof AsphyxiationDamage ||
				damager instanceof ColdDamage) {
			return;
		}
		super.beExposedTo(something, damager);
	}

}

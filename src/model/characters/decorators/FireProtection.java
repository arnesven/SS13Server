package model.characters.decorators;


import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.SmokeInhalationDamage;
import model.items.weapons.Flamer;
import model.items.weapons.Weapon;


public class FireProtection extends CharacterDecorator {

	public FireProtection(GameCharacter chara) {
		super(chara, "Fire Suit");
	}
	
	@Override
	public String getPublicName() {
		String res = "Somebody in a fire suit";
		return res;
	}

//    @Override
//    public Sprite getSprite(Actor whosAsking) {
//        MultipleLayerSprite mult;
//        if (getGender().equals("man")) {
//            mult = new MultipleLayerSprite("manfireprotection", "naked.png", 0);
//        } else {
//            mult = new MultipleLayerSprite("womanfireprotection", "naked.png", 2);
//        }
//        mult.addOntop(new Sprite("body", "suit.png", 0));
//       // mult.addOntop(new Sprite("feet", "shoes.png", 7));
//        return mult;
//    }

    @Override
	public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
		if (weapon instanceof Flamer) {
			performingClient.addTolastTurnInfo(this.getPublicName() + " is unaffected by your attack!");
			this.getActor().addTolastTurnInfo(performingClient.getPublicName() + 
					" tried to attack you with " + weapon.getPublicName(this.getActor()));
			return false;
		}
		return super.beAttackedBy(performingClient, weapon, gameData);
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager.getName().equals("fire")) {
			this.getActor().addTolastTurnInfo("Fire is raging around you.");
			return;
		}

		if (damager instanceof SmokeInhalationDamage) {
			return;
		}
		super.beExposedTo(something, damager);
	}

}

package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.ImpalerWeapon;
import model.items.weapons.Weapon;

public class HorrorCharacter extends GameCharacter {

    public static Weapon hugeClaw = new ImpalerWeapon();;
    private int numforms = 0;


    public HorrorCharacter() {
		super("Stalking Horror", 0, 21.0);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (numforms > 5) {
            return new Sprite("horrorgreater", "alien.png", 19);
        }
        return new Sprite("horrorlesser", "alien.png", 0);
    }

    @Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

	@Override
	public GameCharacter clone() {
		return new HorrorCharacter();
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

	@Override
	public Weapon getDefaultWeapon() {
		return hugeClaw;
	}

	public void setImpalerDamage(List<GameCharacter> forms) {
        numforms = forms.size();
		double d = forms.size() / 2.0 - 0.5;
		hugeClaw.setDamage(d);		
	}
	
	@Override
	public void putOnSuit(SuitItem gameItem) {
		//Do nothing 
	}

}

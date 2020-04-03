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

    public static Weapon hugeClaw = new ImpalerWeapon();
    private ChangelingCharacter ling;


    public HorrorCharacter() {
		super("Stalking Horror", 0, 21.0);
	}

    public void setChangeling(ChangelingCharacter ling) {
        this.ling = ling;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (ling != null && ling.getPower() >= 3) {
            return new Sprite("horrorgreater", "alien.png", 19, getActor());
        }
        return new Sprite("horrorlesser", "alien.png", 0, getActor());
    }

	@Override
	public int getSize() {
		return LARGE_SIZE;
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

	public void setImpalerDamage(double d) {
		hugeClaw.setDamage(d);		
	}
	
	@Override
	public void putOnSuit(SuitItem gameItem) {
		//Do nothing 
	}

}

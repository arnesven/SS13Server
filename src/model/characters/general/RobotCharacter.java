package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import model.items.weapons.BluntWeapon;
import model.items.weapons.Weapon;

public class RobotCharacter extends GameCharacter {

	public RobotCharacter(String string, int i, double d) {
		super(string, i ,d);
		this.setMaxHealth(4.0);
		this.setHealth(4.0);
	}

    @Override
    public boolean wasCriticalHit(Weapon weapon) {
        return false;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("robot", "robots.png", 51);
    }

    @Override
    public GameCharacter clone() {
        return new RobotCharacter(getPublicName(), getStartingRoom(), getSpeed());
    }

    @Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<GameItem>();
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}

	@Override
	public Weapon getDefaultWeapon() {
		return Weapon.STEEL_PROD;
	}
	
	@Override
	public boolean hasInventory() {
		return true;
	}


}

package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import graphics.Sprite;
import model.Actor;
import model.Player;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;

public class HorrorCharacter extends GameCharacter {

    private Weapon hugeClaw = new Weapon("Impaler", 0.75, 1.0, false, 0.0, true);
    private int numforms = 0;


    public HorrorCharacter() {
		super("Stalking Horror", 0, 21.0);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (numforms > 4) {
            return new Sprite("horrorlesser", "alien.png", 0);
        }
        return new Sprite("horrorgreater", "alien.png", 19);
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

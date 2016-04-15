package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;

public class HorrorCharacter extends GameCharacter {

	public HorrorCharacter() {
		super("Stalking Horror", 0, 21.0);
	}

	@Override
	public char getIcon(Player whosAsking) {
		return 'A';
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

	@Override
	public GameCharacter clone() {
		return new HorrorCharacter();
	}
	
	private Weapon hugeClaw = new Weapon("Impaler", 0.75, 1.0, false, 0.0);
	
	@Override
	public Weapon getDefaultWeapon() {
		return hugeClaw;
	}

	public void setImpalerDamage(List<GameCharacter> forms) {
		double d = forms.size() / 2.0 - 0.5;
		hugeClaw.setDamage(d);		
	}
	
	@Override
	public void putOnSuit(SuitItem gameItem) {
		//Do nothing 
	}

}

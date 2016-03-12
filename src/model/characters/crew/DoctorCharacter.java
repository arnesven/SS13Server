package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Knife;
import model.items.weapons.LaserPistol;

public class DoctorCharacter extends GameCharacter {

	public DoctorCharacter() {
		super("Doctor", 24, 12.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Knife());
		return list;
	}

}
package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.Knife;
import model.items.LaserPistol;
import model.items.MedKit;

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

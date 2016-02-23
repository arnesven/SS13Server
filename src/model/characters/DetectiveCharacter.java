package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.items.Revolver;


public class DetectiveCharacter extends GameCharacter {

	public DetectiveCharacter() {
		super("Detective", 12, 13.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Revolver());
		return list;
	}

}

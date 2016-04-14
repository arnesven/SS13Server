package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.characteractions.JackInAction;
import model.actions.characteractions.LaptopRemoteAccessAction;
import model.characters.GameCharacter;
import model.items.general.GameItem;
import model.items.general.Laptop;
import model.items.general.Tools;

public class TechnicianCharacter extends CrewCharacter {

	public TechnicianCharacter() {
		super("Technician", 26, 6.0);
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData, ArrayList<model.actions.Action> at) {
		if (GameItem.hasAnItem(this.getActor(), new Laptop())) {
			Laptop pc = (Laptop)GameItem.getItem(this.getActor(), new Laptop());
			if (pc.isJackedIn() && pc.getJackRoom() != this.getPosition()) {
				pc.setNotJackedIn();
			}
			
			if (pc.isJackedIn()) {
				at.add(new LaptopRemoteAccessAction(pc));
			} else {
				at.add(new JackInAction(pc));
			}
		}
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Laptop());
		list.add(new Tools());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new TechnicianCharacter();
	}

}

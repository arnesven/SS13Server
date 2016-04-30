package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.characteractions.JackInAction;
import model.actions.characteractions.LaptopRemoteAccessAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Laptop;
import model.items.general.Tools;
import util.Logger;

public class TechnicianCharacter extends CrewCharacter {

	public TechnicianCharacter() {
		super("Technician", 26, 6.0);
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
		if (GameItem.hasAnItem(this.getActor(), new Laptop())) {
            Logger.log("Technician had a laptop");
            Laptop pc = null;
            try {
                pc = (Laptop) GameItem.getItem(this.getActor(), new Laptop());
            } catch (NoSuchThingException e) {
                Logger.log(Logger.CRITICAL, "No Laptop found. Abandoning Technicians special action.");
                return;
            }
            if (pc.isJackedIn() && pc.getJackRoom() != getActor().getPosition()) {
				pc.setNotJackedIn();
			}
			
			if (pc.isJackedIn()) {
				at.add(new LaptopRemoteAccessAction(pc));
			} else {
				at.add(new JackInAction(pc));
			}
		} else {
            Logger.log("Technician didn't have a laptop");
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

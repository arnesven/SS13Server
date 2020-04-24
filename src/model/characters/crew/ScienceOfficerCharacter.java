package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.characteractions.JackInAction;
import model.actions.characteractions.LaptopRemoteAccessAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.chemicals.EtherChemicals;
import model.items.general.*;
import util.Logger;

public class ScienceOfficerCharacter extends CrewCharacter {

	public ScienceOfficerCharacter() {
		super("Science Officer", SCIENCE_TYPE, 1, 9.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new EtherChemicals());
		list.add(new Multimeter());
        list.add(new Teleporter());
        list.add(new Laptop());
		return list;
	}

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        if (GameItem.hasAnItem(this.getActor(), new Laptop())) {
            //Logger.log("Science Officer had a laptop");
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
            Logger.log("Science Officer didn't have a laptop");
        }
    }

	@Override
	public GameCharacter clone() {
		return new ScienceOfficerCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 250;
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this, "There's lots of experiments that need overseeing, and some that don't (mainly your own).", "Hacker").makeString();
    }

}

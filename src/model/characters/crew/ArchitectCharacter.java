package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.DoorPartsStack;
import model.items.general.GameItem;
import model.items.general.RoomPartsStack;
import model.items.general.Tools;
import model.items.keycard.EngineeringKeyCard;
import model.items.suits.PowerSuit;

public class ArchitectCharacter extends CrewCharacter {

	public ArchitectCharacter() {
		super("Architect", TECHNICAL_TYPE,26, 6.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new EngineeringKeyCard());
		list.add(new Tools());
        list.add(new RoomPartsStack(6));
        list.add(new DoorPartsStack(4));
        list.add(new PowerSuit());
   		return list;
	}

	@Override
	public GameCharacter clone() {
		return new ArchitectCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 75;
    }


	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "When you need more space out in space, you're the man/woman for the job.", "Roombuilding, Doorbuilding").makeString();
	}

}

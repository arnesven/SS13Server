package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.UniversalKeyCard;
import model.items.general.MedKit;
import model.items.suits.Sweater;

public class HeadOfStaffCharacter extends CrewCharacter {

	public HeadOfStaffCharacter() {
		super("Head of Staff", COMMAND_TYPE,14, 15.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new UniversalKeyCard());
		list.add(new Sweater());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new HeadOfStaffCharacter();
	}

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        //at.add(new InformCrew(gameData)); // not really necessary any more.
    }

    @Override
    public int getStartingMoney() {
        return 150;
    }

	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "As the executive officer on the station, your job is making sure everyone else is doing theirs. You also handle requisitions, job changes and demotions.", "Inform Crew").makeString();
	}
}

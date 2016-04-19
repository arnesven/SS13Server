package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.characteractions.SendATextAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.items.general.MedKit;
import model.items.suits.Sweater;

public class HeadOfStaffCharacter extends CrewCharacter {

	public HeadOfStaffCharacter() {
		super("Head of Staff", 14, 15.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new KeyCard());
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
        at.add(new SendATextAction(gameData));
    }
}

package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.Action;
import model.actions.characteractions.PickUpAllAction;
import model.characters.GameCharacter;
import model.items.general.Chemicals;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;

public class JanitorCharacter extends CrewCharacter {

	public JanitorCharacter() {
		super("Janitor", 23, 3.0);
		
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		PickUpAllAction pua = new PickUpAllAction();
		if (getActor().getPosition().getItems().size() > 0) {
			at.add(pua);
		}
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new FireExtinguisher());
		return list;
	}
	
	public boolean isEncumbered() {
		if (getTotalWeight() >= 8.0) {
			return true;
		}
		
		return false;
	}

	@Override
	public GameCharacter clone() {
		return new JanitorCharacter();
	}

}

package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.Action;
import model.actions.characteractions.PickUpAllAction;
import model.characters.GameCharacter;
import model.items.Chemicals;
import model.items.FireExtinguisher;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Knife;

public class JanitorCharacter extends GameCharacter {

	public JanitorCharacter() {
		super("Janitor", 23, 3.0);
		
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		PickUpAllAction pua = new PickUpAllAction();
		if (this.getPosition().getItems().size() > 0) {
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

}

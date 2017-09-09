package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.PickUpAllAction;
import model.characters.general.GameCharacter;
import model.items.chemicals.HydrogenPeroxideChemicals;
import model.items.general.Chemicals;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.items.general.Tools;

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
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new HydrogenPeroxideChemicals());
		list.add(new FireExtinguisher());
        list.add(new Tools());
		return list;
	}
	
	public boolean isEncumbered() {
        return getTotalWeight() >= 15.0;

    }

	@Override
	public GameCharacter clone() {
		return new JanitorCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 25;
    }
}

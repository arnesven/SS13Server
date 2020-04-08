package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.PickUpAllAction;
import model.characters.general.GameCharacter;
import model.items.chemicals.HydrogenPeroxideChemicals;
import model.items.general.*;
import util.HTMLText;

public class JanitorCharacter extends CrewCharacter {

	public JanitorCharacter() {
		super("Janitor", SUPPORT_TYPE,450, 3.0);
	}


	@Override
	public int getSize() {
		return SMALL_SIZE;
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
		list.add(new Mop());
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

	public  String getJobDescription() {
		return new JobDescriptionMaker(this,"When all goes to hell, it's your duty to clean up the mess. Make sure the station really shines.",
				"Pick upp all, Extra carrying capacity").makeString();
	}

}

package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.characteractions.MarriageAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.general.Bible;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.weapons.AutoCremator;

public class ChaplainCharacter extends CrewCharacter {

	public ChaplainCharacter() {
		super("Chaplain", 2, 2.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new AutoCremator());
		list.add(new Bible());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new ChaplainCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 25;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        Action a = new MarriageAction();
        if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
            at.add(a);
        }

    }
}

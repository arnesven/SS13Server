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
import model.items.keycard.SupportIdentCard;
import model.items.weapons.AutoCremator;

public class ChaplainCharacter extends CrewCharacter {

	public ChaplainCharacter() {
		super("Chaplain", SUPPORT_TYPE, 2, 2.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new SupportIdentCard());
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

    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this, "When the crew needs spiritual " +
                "guidance or consolation they come to the chapel and to you." +
                " For those who don't, it's your duty to preach the holy words.",
                "Sing Sermon").makeString();
    }
}

package model.characters.crew;

import model.GameData;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class QuarterMasterCharacter extends CrewCharacter {
    public QuarterMasterCharacter() {
        super("Quartermaster", CrewCharacter.COMMAND_TYPE, 446, 12.5);
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        return new ArrayList<>();
    }

    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "It's the Quartermaster is responsible for supplying the crewmembers with the things they " +
                        "need to do their jobs. Buy, sell, pack, unpack!", "Pack/Unpack Crate").makeString();
    }

    @Override
    public GameCharacter clone() {
        return new QuarterMasterCharacter();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);

    }
}

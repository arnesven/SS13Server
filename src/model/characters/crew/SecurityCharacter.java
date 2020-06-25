package model.characters.crew;

import model.GameData;
import model.actions.characteractions.SuitUpAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.characters.special.MartialArtist;
import model.items.general.GameItem;
import model.items.general.SecurityRadio;
import model.items.keycard.SecurityKeyCard;
import model.items.suits.SecOffsHelmet;
import model.items.suits.SecOffsVest;

import java.util.ArrayList;
import java.util.List;

public abstract class SecurityCharacter extends CrewCharacter implements MartialArtist {

    public SecurityCharacter(String name, double speed) {
        super(name, SECURITY_TYPE, 18, speed);
    }

    protected abstract void addSpecificSecurityStartingGear(ArrayList<GameItem> list);

    @Override
    public List<GameItem> getCrewSpecificItems() {
        ArrayList<GameItem> list = new ArrayList<>();
        list.add(new SecurityRadio());
        list.add(new SecOffsVest());
        list.add(new SecOffsHelmet());
        list.add(new SecurityKeyCard());
        addSpecificSecurityStartingGear(list);
        return list;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        if (GameItem.hasAnItemOfClass(getActor(), SecOffsVest.class) &&
                GameItem.hasAnItemOfClass(getActor(), SecOffsHelmet.class)) {
            at.add(new SuitUpAction());
        }
    }


    @Override
    public GameCharacter clone() {
        return new SecurityOfficerCharacter();
    }

    @Override
    public int getStartingMoney() {
        return 25;
    }

}

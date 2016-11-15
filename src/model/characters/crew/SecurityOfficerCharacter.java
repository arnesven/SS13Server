package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.SecurityRadio;
import model.items.suits.SecOffsHelmet;
import model.items.suits.SecOffsVest;
import model.items.weapons.StunBaton;

public class SecurityOfficerCharacter extends CrewCharacter {


    private boolean actorSet;

    public SecurityOfficerCharacter() {
		super("Security Officer", 18, 14.0);
        actorSet = false;
	}

    @Override
    public void setActor(Actor c) {
        super.setActor(c);
        if (!actorSet) {
            actorSet = true;
            c.putOnSuit(new SecOffsVest());
            c.putOnSuit(new SecOffsHelmet());
        }
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new StunBaton());
		list.add(new SecurityRadio());
		return list;
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

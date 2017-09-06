package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.characters.general.GameCharacter;
import model.items.HandCuffs;
import model.items.general.GameItem;
import model.items.general.SecurityRadio;
import model.items.suits.SecOffsHelmet;
import model.items.suits.SecOffsVest;
import model.items.weapons.Baton;
import model.items.weapons.StunBaton;

public class SecurityOfficerCharacter extends CrewCharacter {


    private boolean actorSet;

    public SecurityOfficerCharacter() {
		super("Security Officer", 18, 14.0);
        actorSet = false;
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new StunBaton());
        list.add(new Baton());
		list.add(new SecurityRadio());
        list.add(new HandCuffs());
        list.add(new SecOffsVest());
        list.add(new SecOffsHelmet());
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

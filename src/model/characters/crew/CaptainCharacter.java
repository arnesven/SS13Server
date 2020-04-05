package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.AttackAction;
import model.characters.general.GameCharacter;
import model.items.BodyPartFactory;
import model.items.CosmicArtifact;
import model.items.SeveredButt;
import model.items.chemicals.DrugDose;
import model.items.foods.SpaceRum;
import model.items.foods.Vodka;
import model.items.general.*;
import model.items.suits.CaptainsOutfit;
import model.items.weapons.Knife;
import model.items.weapons.LaserSword;
import model.items.weapons.Weapon;
import model.npcs.JungleManNPC;

public class CaptainCharacter extends CrewCharacter {

    public CaptainCharacter() {
        super("Captain", 20, 16.0);
        removeSuit();
        putOnSuit(new CaptainsOutfit(this));
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        ArrayList<GameItem> list = new ArrayList<GameItem>();
        list.add(new KeyCard());

        return list;
    }

    @Override
    public GameCharacter clone() {
        return new CaptainCharacter();
    }

    @Override
    public int getStartingMoney() {
        return 50;
    }


}
package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.HandCuffs;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.SecurityRadio;
import model.items.keycard.SecurityKeyCard;
import model.items.suits.SecOffsHelmet;
import model.items.suits.SecOffsVest;
import model.items.suits.SuitItem;
import model.items.weapons.AutoBrigger;
import model.items.weapons.Baton;
import model.items.weapons.FlashBang;
import model.items.weapons.StunBaton;

import static model.characters.crew.CrewCharacter.SECURITY_TYPE;

public class SecurityOfficerCharacter extends SecurityCharacter {

    private boolean actorSet;

    public SecurityOfficerCharacter() {
		super("Security Officer", 14.0);
        actorSet = false;
	}

    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "You are in charge of security on the station. " +
                        "With your trusty companions, the Securitron and the JudgeBot, you'll keep the peace.", "Suit up!").makeString();
    }


    @Override
    protected void addSpecificSecurityStartingGear(ArrayList<GameItem> list) {
        list.add(new FlashBang());
        list.add(new AutoBrigger());
    }
}

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
import model.items.suits.SecOffsHelmet;
import model.items.suits.SecOffsVest;
import model.items.suits.SuitItem;
import model.items.weapons.Baton;
import model.items.weapons.StunBaton;

public class SecurityOfficerCharacter extends CrewCharacter {


    private boolean actorSet;

    public SecurityOfficerCharacter() {
		super("Security Officer", SECURITY_TYPE, 18, 14.0);
        actorSet = false;
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new StunBaton());
        //list.add(new Baton());
		list.add(new SecurityRadio());
        list.add(new HandCuffs());
        list.add(new SecOffsVest());
        list.add(new SecOffsHelmet());
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

    private class SuitUpAction extends Action {

        public SuitUpAction() {
            super("Suit Up", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "suited up";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            try {
                SuitItem it = GameItem.getItemFromActor(performingClient, new SecOffsVest());
                performingClient.putOnSuit(it);
                performingClient.getItems().remove(it);

                it = GameItem.getItemFromActor(performingClient, new SecOffsHelmet());
                performingClient.putOnSuit(it);
                performingClient.getItems().remove(it);

                performingClient.addTolastTurnInfo("Ready for action!");

            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }

        @Override
        public Sprite getAbilitySprite() {
            return new Sprite("securityoffsuitupabi", "interface_retro.png", 1, null);
        }
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "Any obtrusive, lewd or criminal activity is handled by you. Keep the crewmembers safe from each other!", "Suit up!").makeString();
    }



}

package model.characters.crew;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
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
import model.items.suits.SunGlasses;
import model.items.weapons.StunBaton;
import sounds.Sound;

import java.util.ArrayList;
import java.util.List;

public abstract class SecurityCharacter extends CrewCharacter {

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

    private class SuitUpAction extends Action implements QuickAction {

        public SuitUpAction() {
            super("Suit Up", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        public boolean hasRealSound() {
            return true;
        }

        @Override
        public Sound getRealSound() {
            return new Sound("jumpsuit_equip");
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "suited up";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            try {
                if (GameItem.hasAnItemOfClass(performingClient, SecOffsVest.class)) {
                    SuitItem it = GameItem.getItemFromActor(performingClient, new SecOffsVest());
                    performingClient.putOnSuit(it);
                    performingClient.getItems().remove(it);
                }

                if (GameItem.hasAnItemOfClass(performingClient, SecOffsHelmet.class)) {
                    SuitItem it = GameItem.getItemFromActor(performingClient, new SecOffsHelmet());
                    performingClient.putOnSuit(it);
                    performingClient.getItems().remove(it);
                }

                if (GameItem.hasAnItemOfClass(performingClient, SunGlasses.class)) {
                    SuitItem it = GameItem.getItemFromActor(performingClient, new SunGlasses());
                    performingClient.putOnSuit(it);
                    performingClient.getItems().remove(it);
                }

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

        @Override
        public void performQuickAction(GameData gameData, Player performer) {
            execute(gameData, performer);
        }

        @Override
        public boolean isValidToExecute(GameData gameData, Player performer) {
            return true;
        }

        @Override
        public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
            return performer.getPosition().getClients();
        }
    }

}

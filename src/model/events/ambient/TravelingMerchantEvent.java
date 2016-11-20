package model.events.ambient;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.Room;
import model.npcs.MerchantNPC;
import model.npcs.NPC;
import model.objects.MerchantWaresCrate;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 15/11/16.
 */
public class TravelingMerchantEvent extends AmbientEvent {
    private static final double occurranceChance = 0.02;
    private boolean hasHappened = false;
    private MerchantNPC merchant;
    private MerchantWaresCrate crate;
    private boolean leftAlready = false;
    private boolean addedToMostWanted = false;

    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            merchantArrives(gameData);
        } else {
            if (hasHappened && !leftAlready) {
                if (!merchant.isDead() && merchant.getHealth() < merchant.getMaxHealth()) {
                    merchantLeaves(gameData);
                } else if (merchant.isDead() & merchant.getCharacter().getKiller() != null && !addedToMostWanted) {
                    addKillerToMostWanted(gameData, merchant.getCharacter().getKiller());
                    addedToMostWanted = true;
                }
            }
        }
    }

    private void addKillerToMostWanted(GameData gameData, Actor killer) {
        if (killer instanceof Player) {
            try {
                MostWantedCriminals.add(gameData.getClidForPlayer((Player) killer));
                gameData.getGameMode().getMiscHappenings().add(killer.getBaseName() + " (" +
                        gameData.getClidForPlayer((Player) killer) +
                        ") murdered the merchant and is now wanted by the <i>Galactic Federal Marshals</i>.");
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }

    private void merchantLeaves(GameData gameData) {
        gameData.getNPCs().remove(merchant);
        merchant.getPosition().getObjects().remove(crate);

        try {
            Logger.log(Logger.INTERESTING, "Trying to remove merchant from: " + merchant.getPosition().getName());
            merchant.getPosition().removeNPC(merchant);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("The traveling merchant left the station.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        leftAlready = true;
    }

    private void merchantArrives(GameData gameData) {
        hasHappened = true;
        try {
            Room shuttleGate = gameData.getRoom("Shuttle Gate");
            merchant = new MerchantNPC(shuttleGate);
            gameData.addNPC(merchant);
            crate = new MerchantWaresCrate(shuttleGate, merchant);
            shuttleGate.addObject(crate);

            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation("A traveling merchant has arrived on the station.", gameData);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }
}

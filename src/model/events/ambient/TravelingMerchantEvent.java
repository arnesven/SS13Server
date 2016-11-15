package model.events.ambient;

import model.Actor;
import model.GameData;
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
    private static final double occurranceChance = 2;
    private boolean hasHappened = false;
    private MerchantNPC merchant;
    private MerchantWaresCrate crate;
    private boolean leftAlready = false;

    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            hasHappened = true;
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
        } else {
            if (!leftAlready && !merchant.isDead() && merchant.getHealth() < merchant.getMaxHealth()) {
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

package model.events.ambient;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.AirLockRoom;
import model.map.rooms.DockingPointRoom;
import model.map.rooms.MerchantShip;
import model.map.rooms.Room;
import model.npcs.MerchantNPC;
import model.objects.general.MerchantWaresCrate;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 15/11/16.
 */
public class TravelingMerchantEvent extends AmbientEvent {
    private static final double occurranceChance = AmbientEvent.everyNGames(3);
    private boolean hasHappened = false;
    private MerchantNPC merchant;
    private MerchantWaresCrate crate;
    private boolean leftAlready = false;
    private boolean addedToMostWanted = false;
    private MerchantShip merchantShip;

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

        merchantShip.undockYourself(gameData);
        for (Actor a : merchantShip.getActors()) {
            try {
                a.setPosition(gameData.getRoom("Shuttle Gate"));
                a.addTolastTurnInfo("The merchant ship left, you went to the Shuttle Gate.");
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
        gameData.getMap().moveRoomToLevel(merchantShip, "prison planet", "merchant outpost");

        leftAlready = true;
    }

    private void merchantArrives(GameData gameData) {
        this.merchantShip = new MerchantShip(gameData);
        DockingPoint dp;
        try {
            dp = ((DockingPointRoom)gameData.getRoom("Airlock #2")).getDockingPoints().get(0);
            if (dp.isVacant() && merchantShip.canDockAt(gameData, dp)) {
                gameData.getMap().addRoom(merchantShip, GameMap.STATION_LEVEL_NAME,
                        gameData.getMap().getAreaForRoom(GameMap.STATION_LEVEL_NAME, dp.getRoom()));
                merchantShip.dockYourself(gameData, dp);
            } else {
                Logger.log("Could not find or dock with docking point at Air Lock #2, merchant won't come");
                return;
            }
        } catch (NoSuchThingException e) {
            Logger.log("Could not find or dock with docking point at Air Lock #2, merchant won't come");
            return;
        }


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

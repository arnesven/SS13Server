package model.events.ambient;

import graphics.sprites.Sprite;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.itemactions.MoveInAndPutOutFireAction;
import model.actions.itemactions.PutOutFireAction;
import model.actions.roomactions.CloseAllFireDoorsActions;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.events.Event;
import model.events.RoomPressureEvent;
import model.events.animation.AnimatedSprite;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.map.doors.ElectricalDoor;
import model.map.floors.BurntFloorSet;
import model.objects.consoles.AIConsole;
import model.objects.decorations.BurnMark;
import sounds.Sound;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class ElectricalFire extends OngoingEvent {

	private static final double SPREAD_CHANCE = 0.05;
    private static final double BURNOUT_CHANCE = 0.025;
    private static final double occurenceChance = 0.065; // 6.5% per turn
    private static final double IGNITE_CHANCE = 0.05;
    private static final double RAGING_CHANCE = 0.15;
    private static final double AI_INTERVENTION_CHANCE = 0.25;
    private static final double SMOKE_CHANCE_IN_ROOM_PER_TURN = 0.2;

    private boolean isRaging;
    private boolean aiIntervened = false;

    public ElectricalFire() {
        isRaging = false;
    }

    @Override
    protected double getStaticProbability() {
        return occurenceChance;
    }

    @Override
    public void fix() {
        super.fix();
        if (!isRaging) {
            this.getRoom().addObject(new BurnMark(getRoom()));
        }
    }

    @Override
	public SensoryLevel getSense() {
		return SensoryLevel.FIRE;
	}
	
	
	@Override
	public String howYouAppear(Actor whosAsking) {
		if (isRaging) {
		    return "FIRE!!!";
        }
        return "Fire!";
	}
	
	@Override
	public ElectricalFire clone() {
		return new ElectricalFire();
	}

	protected void maintain(GameData gameData) {
        Logger.log("Maintaining fire in " + getRoom().getName());
        affectActors(gameData);
        boolean anyAroundHasFire = checkForSpread();
        boolean burntOut = checkForBurnout(anyAroundHasFire);
        if (!burntOut) {
            checkForRaging();
            maybeMakeSmoke(gameData);
            checkForAIIntervention(gameData);
        }
		getRoom().addToEventsHappened(this);
	}

    private void maybeMakeSmoke(GameData gameData) {
        if (!SmokeEvent.roomAlreadyHasSmoke(getRoom()) && !roomHasLowPressure()) {
            if (MyRandom.nextDouble() < SMOKE_CHANCE_IN_ROOM_PER_TURN * (isRaging?2.0:1.0)) {
                SmokeEvent ev = new SmokeEvent(this);
                gameData.addEvent(ev);
                getRoom().addEvent(ev);
            }
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isRaging) {
            return new AnimatedSprite("electricalfirefillwholeroom", "fire.png", 5, 8, 32, 32, this, 10, true);
        }
        return new AnimatedSprite("electricalfire", "fire.png", 5, 8, 32, 32, this, 10, true);
    }

    @Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasFire();
	}


	@Override
	public String getDistantDescription() {
		if (isRaging) {
		    return "You hear a roaring fire!";
        }
        return "Something is burning...";
	}

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("fire");
    }

    @Override
    public boolean hasAmbientSound() {
        return true;
    }

    @Override
    public Sound getAmbientSound() {
        return new Sound("fire-burning");
    }


    @Override
    public void experienceFor(Player p) {
        if (p.getPosition() != this.getRoom()) {
            super.experienceFor(p);
        }
    }


    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        List<Action> acts = new ArrayList<>();

            try {
                FireExtinguisher fe = GameItem.getItemFromActor(forWhom, new FireExtinguisher());
                if (fe.getUsesRemaining() > 0) {
                    if (this.getRoom() == forWhom.getPosition()) {
                        PutOutFireAction putOutFireAction = new PutOutFireAction(fe);
                        acts.add(putOutFireAction);
                    } else if (forWhom.findMoveToAblePositions(gameData).contains(this.getRoom())) {
                        MoveAction mipofa = new MoveInAndPutOutFireAction(gameData, forWhom, fe, this.getRoom());
                        acts.add(mipofa);
                    }
                }
            } catch (NoSuchThingException e) {
                Logger.log("No fire ext found for " + forWhom);
            }

        return acts;
    }

    public boolean isRaging() {
        return isRaging;
    }

    public void setRaging(boolean b) {
        isRaging = b;
    }

    @Override
    public void gotRemovedFromRoom(Room room) {
        super.gotRemovedFromRoom(room);
        room.getEffects().remove(Sprite.blankSprite());
    }

    @Override
    public void gotAddedToRoom(Room room) {
        room.addEffect(Sprite.blankSprite());
    }

    private boolean checkForSpread() {
        boolean result = false;
        for (Room neighbor : getRoom().getNeighborList()) {
            if (MyRandom.nextDouble() < getSpreadChance()) {
                Logger.log(Logger.INTERESTING,
                        "  Fire spread to " + neighbor.getName() + "!");
                startNewEvent(neighbor);
            }
            if (neighbor.hasFire()) {
                result = true;
            }
        }
        return result;
    }

    private void checkForAIIntervention(GameData gameData) {
        if (!aiIntervened) {
            try {
                AIConsole cons = gameData.findObjectOfType(AIConsole.class);
                if (cons.AIIsPlayer()) {
                    cons.informOnStation("Warning! Severe fire in " + getRoom().getName(), gameData);
                    aiIntervened = true;
                } else {
                    if (MyRandom.nextDouble() < AI_INTERVENTION_CHANCE && isRaging) {
                        if ((cons.isCorrupt() && !noHumansInRoom()) || (!cons.isCorrupt() && noHumansInRoom())) {
                            cons.informOnStation("Warning! Severe fire in " + getRoom().getName() +
                                    ". Please keep out until fire is contained.", gameData);
                            for (ElectricalDoor d : CloseAllFireDoorsActions.findDoors(gameData, getRoom())) {
                                d.shutFireDoor(gameData);
                            }
                            aiIntervened = true;
                        }
                    }

                }

            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean noHumansInRoom() {
        for (Actor a : getRoom().getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter)) {
                return false;
            }
        }
        return true;
    }

    private double getBurnoutChance() {
        double isRagingModifier = 0.25;
        if (roomHasNoPressure()) {
            return BURNOUT_CHANCE * 10.0 * isRagingModifier;
        }
        if (getRoom().hasHullBreach() || roomHasLowPressure()) {
            return BURNOUT_CHANCE * 3.0 * isRagingModifier;
        }
        if (isRaging) {
            return BURNOUT_CHANCE * isRagingModifier;
        }
        return BURNOUT_CHANCE;
    }

    private boolean roomHasLowPressure() {
        return getPressureEvent().getPressureSimulation().getPressureFor(getRoom()) < 0.75;
    }

    private boolean roomHasNoPressure() {
        return getPressureEvent().getPressureSimulation().getPressureFor(getRoom()) < 0.02;
    }


    private double getSpreadChance() {
        if (isRaging) {
            return SPREAD_CHANCE * 1.25;
        }
        return SPREAD_CHANCE * 0.75;
    }

    private void affectActors(GameData gameData) {
        for (Target t : getRoom().getTargets(gameData)) {
            if (t instanceof Actor) {
                Actor targetAsActor = (Actor)t;
                if (! targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
                    if (MyRandom.nextDouble() < getIgniteChance(targetAsActor)) {
                        targetAsActor.setCharacter(new OnFireCharacterDecorator(targetAsActor.getCharacter()));
                    } else {
                        if (MyRandom.nextDouble() < getDamageChance(targetAsActor)) {
                            t.beExposedTo(null, new FireDamage(), gameData);
                        }
                    }
                } else {
                    // Do nothing, burn decorator will damage actor this round.
                }
            } else {
                t.beExposedTo(null, new FireDamage(), gameData);
            }
        }
    }

    private double getDamageChance(Actor targetAsActor) {
        if (targetAsActor instanceof Player) {
            if (((Player) targetAsActor).getNextAction() instanceof MoveAction) { // Moved this round.
                if (isRaging) {
                    return 0.75;
                } else {
                    return 0.25;
                }
            }
        }

        return 1.0;
    }

    private double getIgniteChance(Actor targetAsActor) {
        return IGNITE_CHANCE;
    }


    private boolean checkForBurnout(boolean anyAroundHasFire) {
        if (!anyAroundHasFire) {
            if (MyRandom.nextDouble() < getBurnoutChance()) {
                this.fix();
                return true;
            }
        }
        return false;
    }

    private void checkForRaging() {
        if (!isRaging && MyRandom.nextDouble() < RAGING_CHANCE) {
            isRaging = true;
            getRoom().setFloorSet(new BurntFloorSet(getRoom().getFloorSet()));
        }
    }

}

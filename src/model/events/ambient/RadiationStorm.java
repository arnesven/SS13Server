package model.events.ambient;

import graphics.sprites.Sprite;
import model.events.Event;
import model.events.damage.RadiationDamage;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.GeigerMeter;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AvoidRoomsMovement;
import model.npcs.behaviors.MeanderingHumanMovement;

public class RadiationStorm extends AmbientEvent {

	private int roundsLeft = -1;
	private double damage;
	private int side;
    private static final double occurenceChance = 0.03;

    @Override
    protected double getStaticProbability() {
        return occurenceChance;
    }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("radiationstorm", "decals2.png", 2, 6);
    }

    @Override
	public void apply(GameData gameData) {
		if (roundsLeft > 0) {
			maintainStorm(gameData);
			
		} else if (roundsLeft == 0) {
			endRadiationstorm(gameData);

		} else if (MyRandom.nextDouble() < getProbability()) {
			startNewRadiationStorm(gameData);
		}
	}

	private void maintainStorm(GameData gameData) {
		roundsLeft--;
		for (Room r : gameData.getMap().getSideLocations().get(side)) {
			hurtActorsInRoom(r, this.damage, gameData);
		}
		this.damage = randomDamage();
		
	}


	public void hurtActorsInRoom(Room r, double damage2, GameData gameData) {
		for (Actor a : r.getActors()) {
			a.getAsTarget().beExposedTo(null, new RadiationDamage(damage2, gameData));
		}
	}

	private void endRadiationstorm(GameData gameData) {
		for (Player p : gameData.getPlayersAsList()) {
			p.addTolastTurnInfo("AI; \"Radiation storm has passed.\"");
		}
		
		for (Room r : gameData.getMap().getSideLocations().get(side)) {
			r.removeEvent(this);
		}
		
		for (NPC npc : gameData.getNPCs()) {
			if (npc.getMovementBehavior() instanceof AvoidRoomsMovement) {
				npc.setMoveBehavior(new MeanderingHumanMovement(0.25));
			}
		}
		roundsLeft--;
	}

	private void startNewRadiationStorm(GameData gameData) {
		roundsLeft = MyRandom.nextInt(3) + 2;
		this.damage = randomDamage();
		side = MyRandom.nextInt(4);
		String sideStr = gameData.getMap().getSideString(side);
		
		for (Player p : gameData.getPlayersAsList()) {
			p.addTolastTurnInfo("AI; \"Radiation storm detected, please evacuate " + sideStr + " side of station.\"");
		}
		
		for (Room r : gameData.getMap().getSideLocations().get(side)) {
			r.addEvent(this);
		}
		
		for (NPC npc : gameData.getNPCs()) {
			if (npc.getMovementBehavior() instanceof MeanderingHumanMovement) {
				npc.setMoveBehavior(new AvoidRoomsMovement(gameData.getMap().getSideLocations().get(side)));
			}
		}
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		String name = "Radiation";
		if (hasGeigerMeter(performingClient)) {
			if (getDamage() == 0.0) {
				name += " (harmless)";
			} else if (getDamage() == 1.0) {
				name += " (severe)";
			}
		}
		return name;
	}

	private boolean hasGeigerMeter(Actor performingClient) {
		for (GameItem gi : performingClient.getItems()) {
			if (gi instanceof GeigerMeter) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}
	
	private static double randomDamage() {
		return ((MyRandom.nextInt(4)<3)?0.5:0) + ((MyRandom.nextInt(4)==3)?0.5:0.0);
	}

	public double getDamage() {
		return damage;
	}
	
}

package model.events;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.items.GeigerMeter;
import model.map.Room;
import model.npcs.AvoidRoomsMovement;
import model.npcs.MeanderingHumanMovement;
import model.npcs.NPC;

public class RadiationStorm extends Event {

	private int roundsLeft = -1;
	private double damage;
	private int side;

	@Override
	public double getProbability() {
		return 1.03;
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
			for (Actor a : r.getActors()) {
				a.getAsTarget().beExposedTo(null, new RadiationDamage(this.damage));
			}
		}
		this.damage = randomDamage();
		
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
		String sideStr = "aft";
		if (side == 1) {
			sideStr = "port";
		} else if (side == 2) {
			sideStr = "front";
		} else if (side == 3) {
			sideStr = "starboard";
		}
		
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
	
	@Override
	public String addYourselfToRoomInfo(Player whosAsking) {
		return "r" + howYouAppear(whosAsking);
	}
	
	private static double randomDamage() {
		return ((MyRandom.nextInt(4)<3)?0.5:0) + ((MyRandom.nextInt(4)==3)?0.5:0.0);
	}

	public double getDamage() {
		return damage;
	}
	
}

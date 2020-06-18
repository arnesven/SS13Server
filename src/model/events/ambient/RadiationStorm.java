package model.events.ambient;

import graphics.sprites.Sprite;
import model.events.Event;
import model.events.damage.RadiationDamage;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.objects.consoles.AIConsole;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.Multimeter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AvoidRadiationMovement;
import model.npcs.behaviors.MeanderingHumanMovement;

public class RadiationStorm extends AmbientEvent {

	private int roundsLeft = -1;
	private double damage;
	private int side;
    private static final double occurenceChance = 0.015;
	private static final Sprite radStormeffect = new Sprite("radiationstormeffect", "alert.png", 5, 1, null);

    @Override
    protected double getStaticProbability() {
        return occurenceChance;
    }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("radiationstorm", "decals2.png", 2, 6, this);
    }

	@Override
	public boolean showSpriteInRoom() {
		return false;
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
		for (Room r : gameData.getMap().getArea("ss13", GameMap.getSideString(side))) {
			hurtActorsInRoom(r, this.damage, gameData);
		}
		this.damage = randomDamage();
		
	}


	public void hurtActorsInRoom(Room r, double damage2, GameData gameData) {
		for (Actor a : r.getActors()) {
			a.getAsTarget().beExposedTo(null, new RadiationDamage(damage2, gameData), gameData);
		}
	}

	private void endRadiationstorm(GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Radiation storm has passed.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

		for (Room r : gameData.getMap().getArea("ss13", GameMap.getSideString(side))) {
			r.removeEvent(this);
		}
		
		for (NPC npc : gameData.getNPCs()) {
			if (npc.getMovementBehavior() instanceof AvoidRadiationMovement) {
				npc.setMoveBehavior(new MeanderingHumanMovement(0.25));
			}
		}
		roundsLeft--;
	}

	private void startNewRadiationStorm(GameData gameData) {
		roundsLeft = MyRandom.nextInt(3) + 2;
		this.damage = randomDamage();
		side = MyRandom.nextInt(4);
		String sideStr = GameMap.getSideString(side);

        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Radiation storm detected, please evacuate " + sideStr + " side of station.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


        for (Room r : gameData.getMap().getArea("ss13", GameMap.getSideString(side))) {
			r.addEvent(this);
		}
		
		for (NPC npc : gameData.getNPCs()) {
			if (npc.getMovementBehavior() instanceof MeanderingHumanMovement) {
				npc.setMoveBehavior(new AvoidRadiationMovement(gameData.getMap().getArea("ss13", GameMap.getSideString(side))));
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
			if (gi instanceof Multimeter) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                                SensoryLevel.AudioLevel.INAUDIBLE,
                                SensoryLevel.OlfactoryLevel.UNSMELLABLE);
	}
	
	private static double randomDamage() {
		return ((MyRandom.nextInt(4)<3)?0.5:0) + ((MyRandom.nextInt(4)==3)?0.5:0.0);
	}

	public double getDamage() {
		return damage;
	}

	@Override
	public void gotAddedToRoom(Room room) {
		room.addEffect(radStormeffect);
	}

	@Override
	public void gotRemovedFromRoom(Room room) {
		room.getEffects().remove(radStormeffect);
	}


	public static boolean hasEvent(Room position) {
    	for (Event e : position.getEvents()) {
    		if (e instanceof RadiationStorm) {
    			return true;
			}
		}
		return false;
	}

}

package model.items.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.Hazard;
import model.characters.general.GameCharacter;
import model.characters.crew.*;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.events.damage.ExplosiveDamage;
import model.items.NoSuchThingException;
import model.items.foods.ExplodingFood;
import model.map.Room;
import model.events.SpontaneousExplosionEvent;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import util.Logger;
import util.MyRandom;

public class BombItem extends HidableItem implements ExplodableItem {

	public static final String FOUND_A_BOMB_STRING = "What's this... a bomb!?";
    private static final double CHAIN_DETONATION_CHANCE = 0.5;
    private final String name;
    private GameItem concealedWithin;
    private boolean exploded;
    private static int maxChain = 0;

    public BombItem(String string) {
		super(string, 2.0);
        this.name = string;
        exploded = false;
	}

	public static String getOperationString() {
		return "fiddled with bomb";
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bombitem", "assemblies.png", 43);
    }
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		if (!this.isHidden()) {
			if (isDemolitionsExpert(whosAsking)) {
				super.addYourselfToRoomInfo(info, whosAsking);
			} else {
				info.add(getSprite(whosAsking).getName() + "<img>" + "Bomb");
			}
		}
	}
	
	@Override
	public String getPublicName(Actor whosAsking) {
		if (isDemolitionsExpert(whosAsking)) {
			return getFullName(whosAsking);
		}
		return "Bomb";
	}

	private boolean isDemolitionsExpert(Actor whosAsking) {
		if (whosAsking != null) { 
			GameCharacter chara = whosAsking.getCharacter();
			return chara instanceof DetectiveCharacter ||
					chara instanceof EngineerCharacter;
		}
		return false;
	}
	
	public void explode(final GameData gameData, final Actor performingClient) {
        Logger.log(Logger.INTERESTING,
                    "Exploding bomb.");
        Room bombRoom = null;
        try {
            bombRoom = gameData.findRoomForItem(this);
            Logger.log(Logger.INTERESTING, "bomb room is " + bombRoom.getName());
        } catch (NoSuchThingException e) {
            Logger.log(Logger.INTERESTING,
                    "Bomb was not found in a room.");
           // bombRoom continues to be null;
        }


        Actor currentCarrier = null;
        try {
            currentCarrier = gameData.findActorForItem(this);
            Logger.log("Removing bomb from carrier");
            currentCarrier.getItems().remove(this);

            currentCarrier.getCharacter().beExposedTo(performingClient, new ExplosiveDamage(3.0));
            bombRoom = currentCarrier.getPosition();
        } catch (NoSuchThingException e) {
            if (bombRoom == null) {
                Logger.log(Logger.CRITICAL,
                        " COULD NOT FIND BOMB, WHERE DID IT GO?!");
                exploded = true;
                return;
            }
        }


		if (bombRoom != null) {
			bombRoom.removeFromRoom(this);
            if (concealedWithin != null) {
                bombRoom.removeFromRoom(concealedWithin);
            }
		}

        handleWithin(gameData, bombRoom, performingClient);

        for (Actor a : bombRoom.getActors()) {
			if (a != currentCarrier) {
				a.getCharacter().beExposedTo(performingClient, 
						new ExplosiveDamage(2.0));
			}
		}

		for (Object o : bombRoom.getObjects()) {
			if (o instanceof Target) {
				((Target)o).beExposedTo(performingClient, 
						new ExplosiveDamage(2.0));
			}
		}
        exploded = true;
        possiblyAddHazards(gameData, bombRoom);
        explodeOtherBombsInRoom(gameData, performingClient, bombRoom);
        bombRoom.addToEventsHappened(new SpontaneousExplosionEvent());

	}

    private void explodeOtherBombsInRoom(GameData gameData, Actor performingClient, Room bombRoom) {
        List<BombItem> bombList = new ArrayList<>();
        for (GameItem it : bombRoom.getItems()) {
            if (getIfIsAnUnexplodedBomb(it) != null) {
                bombList.add(getIfIsAnUnexplodedBomb(it));
            }
        }

        for (Actor a : bombRoom.getActors()) {
            for (GameItem it : a.getItems()) {
                if (getIfIsAnUnexplodedBomb(it) != null) {
                    bombList.add(getIfIsAnUnexplodedBomb(it));
                }
            }
        }

        for (GameObject ob : bombRoom.getObjects()) {
            if (ob instanceof ContainerObject) {
                for (GameItem it : ((ContainerObject) ob).getInventory()) {
                    if (getIfIsAnUnexplodedBomb(it) != null) {
                        bombList.add(getIfIsAnUnexplodedBomb(it));
                    }
                }
            }
        }

        int chain = 1;
        for (BombItem it : bombList) {
            if (MyRandom.nextDouble() < CHAIN_DETONATION_CHANCE) {
                it.explode(gameData, performingClient);
                chain++;
            }
        }

        List<Actor> surrounding = new ArrayList<>();
        for (Room r : bombRoom.getNeighborList()) {
            for (Actor a : r.getActors()) {
                if (!bombRoom.getActors().contains(a)) {
                    surrounding.add(a);
                }
            }
        }

        if (chain > 1) {
            for (Actor a : surrounding) {
                a.getCharacter().beExposedTo(performingClient, new ExplosiveDamage(((double) chain-1) / 2.0));
            }
        }

        if (chain > maxChain) {
            setMaxChain(gameData, chain);
        }
    }

    private BombItem getIfIsAnUnexplodedBomb(GameItem it) {
        if (it.getTrueItem() instanceof BombItem) {
            if (!((BombItem) it.getTrueItem()).isExploded()) {
                return (BombItem)it.getTrueItem();
            }
        }
        return null;
    }

    private void handleWithin(GameData gameData, Room bombRoom, Actor bomber) {
        if (this.concealedWithin != null) {
            Actor currentCarrier = null;
            for (Actor a : bombRoom.getActors()) {
                a.addTolastTurnInfo("The " + concealedWithin.getBaseName() + " exploded!");
                if (a.getItems().contains(concealedWithin)) {
                    currentCarrier = a;
                }
            }

            if (currentCarrier != null && currentCarrier.getItems().contains(concealedWithin)) {
                Logger.log(Logger.INTERESTING, currentCarrier.getBaseName() + " was carrying a " +
                        concealedWithin.getBaseName() + " with a bomb in it.");
                currentCarrier.getItems().remove(concealedWithin);
                currentCarrier.getCharacter().beExposedTo(bomber,
                        new ExplosiveDamage(1.0));
            } else if (bombRoom.getItems().contains(concealedWithin)) {
                bombRoom.getItems().remove(concealedWithin);
            }
        }
    }


    private void possiblyAddHazards(GameData gameData, final Room bombRoom) {
        new Hazard(gameData) {

            @Override
            public void doHazard(GameData gameData) {
                // breach the hull?
                if (MyRandom.nextDouble() < 0.75) {
                    HullBreach hull = ((HullBreach) gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(bombRoom);
                    Logger.log(Logger.INTERESTING,
                            "breached the hull with bomb!");
                }

                // start a fire?
                if (MyRandom.nextDouble() < 0.25) {
                    ElectricalFire fire = ((ElectricalFire) gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(bombRoom);
                    Logger.log(Logger.INTERESTING,
                            "started a fire with bomb!");
                }
            }

        };
    }

    @Override
    public GameItem getAsItem() {
        return this;
    }

    @Override
    public void explode(GameData gameData, Room room, Actor maker) {
        this.explode(gameData, maker);
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {
        this.concealedWithin = explodingFood;
    }

    @Override
    public GameItem clone() {
        return new BombItem(name);
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setMaxChain(GameData gameData, int max) {
        gameData.getGameMode().setMaxBombChain(max);
    }

    public void defuse(GameData gameData) {
        exploded = true;
        gameData.getGameMode().addToDefusedBombs(1);
    }
}

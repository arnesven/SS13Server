package model.items.weapons;


import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.Hazard;
import model.actions.itemactions.BurnHiveAction;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.SprayFireAction;
import model.events.ambient.ElectricalFire;
import model.items.chemicals.Chemicals;
import model.items.general.GameItem;
import model.objects.general.GameObject;
import model.objects.general.HiveObject;
import util.Logger;
import util.MyRandom;

public class Flamer extends Weapon {

	public static final SensoryLevel SENSED_AS = SensoryLevel.FIRE;
    public static final int CHEMS_NEEDED_TO_BURN_HIVE = 1;

    public Flamer() {
		super("Flamer", 0.75, 0.5, false, 1.5, 159);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("flamer", "weapons.png", 14, this);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("flamerhandheld", "items_righthand.png", 16, 18, this);
    }

    @Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        List<Chemicals> chem = getChemicalsFromClient(cl);
        if (chem.size() >= CHEMS_NEEDED_TO_BURN_HIVE) {
            possiblyAddBurnHive(at, cl);
        }

        addSprayFireAction(at, cl);
    }

	private void addSprayFireAction(ArrayList<Action> at, Actor cl) {
		at.add(new SprayFireAction());
		
	}

	private void possiblyAddBurnHive(ArrayList<Action> at, Actor cl) {
		for (GameObject ob : cl.getPosition().getObjects()) {
			if (ob instanceof HiveObject){
				if (((HiveObject)ob).isFound()) {
					at.add(new BurnHiveAction((HiveObject)ob));
				}
			}
		}
	}

	private List<Chemicals> getChemicalsFromClient(Actor cl) {
		List<Chemicals> list = new ArrayList<>();
		for (GameItem gi : cl.getItems()) {
			if (gi instanceof Chemicals && ((Chemicals) gi).isFlammable()) {
				list.add((Chemicals)gi);
			}
		}
		return list;
	}
	
	@Override
	public Weapon clone() {
		return new Flamer();
	}

    @Override
    protected void checkHazard(final Actor performingClient, GameData gameData) {
        new Hazard(gameData) {

            @Override
            public void doHazard(GameData gameData) {
                Logger.log(Logger.INTERESTING,
                        "Will flamer start a fire?...", false);
                if (MyRandom.nextDouble() < 0.10) {
                    Logger.log(Logger.INTERESTING,
                            "Yes! ");
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    Logger.log(Logger.INTERESTING,
                            performingClient.getBaseName() + " started a fire!");
                } else {
                    Logger.log(Logger.INTERESTING,
                            "no", false);
                }
            }
        };
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An excellent weapon for exterminating vermin. Can be used to burn a hive immediately if you have some flammable chemicals.";
    }
}

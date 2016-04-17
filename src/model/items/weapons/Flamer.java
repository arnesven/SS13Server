package model.items.weapons;


import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.HazardAction;
import model.actions.itemactions.BurnHiveAction;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.SprayFireAction;
import model.events.ambient.ElectricalFire;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.objects.general.GameObject;
import model.objects.general.HiveObject;
import util.MyRandom;

public class Flamer extends Weapon {

	public static final SensoryLevel SENSED_AS = SensoryLevel.FIRE;

	public Flamer() {
		super("Flamer", 0.75, 0.5, false, 2.0);
	}
	
	@Override
	protected char getIcon() {
		return '{';
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {

        List<Chemicals> chem = getChemicalsFromClient(cl);
        if (chem.size() >= 2) {
            possiblyAddBurnHive(at, cl);
        }

        addSprayFireAction(at, cl);
    }

	private void addSprayFireAction(ArrayList<Action> at, Player cl) {
		at.add(new SprayFireAction());
		
	}

	private void possiblyAddBurnHive(ArrayList<Action> at, Player cl) {
		for (GameObject ob : cl.getPosition().getObjects()) {
			if (ob instanceof HiveObject){
				if (((HiveObject)ob).isFound()) {
					at.add(new BurnHiveAction((HiveObject)ob));
				}
			}
		}
	}

	private List<Chemicals> getChemicalsFromClient(Player cl) {
		List<Chemicals> list = new ArrayList<>();
		for (GameItem gi : cl.getItems()) {
			if (gi instanceof Chemicals) {
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
        gameData.executeAtEndOfRound(new HazardAction() {

            @Override
            public void doHazard(GameData gameData) {
                System.out.print("Will flamer start a fire?...");
                if (MyRandom.nextDouble() < 0.10) {
                    System.out.print("Yes! ");
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    System.out.println(performingClient.getBaseName() + " started a fire!");
                } else {
                    System.out.println("no");
                }
            }
        });
    }

}

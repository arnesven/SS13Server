package model.items.weapons;


import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.itemactions.BurnHiveAction;
import model.actions.SensoryLevel;
import model.actions.itemactions.SprayFireAction;
import model.items.Chemicals;
import model.items.GameItem;
import model.objects.GameObject;
import model.objects.HiveObject;

public class Flamer extends Weapon {

	public static final SensoryLevel SENSED_AS = SensoryLevel.FIRE;

	public Flamer() {
		super("Flamer", 0.75, 0.5, false, 2.0);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		
		List<Chemicals> chem = getChemicalsFromClient(cl);
		if (chem.size() >= 3) {
			possiblyAddBurnHive(at, cl);
		}
		
		if (chem.size() >= 1) {
			addSprayFireAction(at, cl);
		}
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

}

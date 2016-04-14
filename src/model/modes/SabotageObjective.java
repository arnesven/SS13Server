package model.modes;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.items.general.Locatable;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;

public class SabotageObjective implements TraitorObjective {

	private List<BreakableObject> sabObjects;
	private GameData gameData;
	private boolean wasCompleted = false;

	public SabotageObjective(GameData gameData, List<BreakableObject> sabObjects) {
		this.sabObjects = sabObjects;
		this.gameData = gameData;
	}

	@Override
	public String getText() {
		String res = "Destroy the " + getUniqueName(sabObjects.get(0));
				
		for (int i = 1; i < sabObjects.size() ; ++i) {
			if (i == sabObjects.size() - 1) {
				res += " and the ";
			} else {
				res += ", the ";
			}
			
			res += getUniqueName(sabObjects.get(i));
		}
		
		return res;
	}

	private String getUniqueName(GameObject gameObject) {
		String res = gameObject.getBaseName();
		if (moreThanOneExists(gameObject)) {
			res += " in the " + gameObject.getPosition().getName();
		}
		return res;
	}

	private boolean moreThanOneExists(GameObject searched) {
		int count = 0;
		for (GameObject ob : gameData.getObjects()) {
			if (ob.getBaseName().equals(searched.getBaseName())) {
				count++;
			}
		}
		return count > 1;
	}

	@Override
	public boolean isCompleted(GameData gameData) {
		boolean allBroken = true;
		for (BreakableObject ob : sabObjects) {
			if (!ob.isBroken()) {
				allBroken = false;
			}
		}
		if (allBroken) {
			wasCompleted  = true;
		}
		return allBroken;
	}

	@Override
	public boolean wasCompleted() {
		return wasCompleted;
	}

	@Override
	public int getPoints() {
		return 500;
	}

	@Override
	public Locatable getLocatable() {
		for (BreakableObject ob : sabObjects) {
			if (!ob.isBroken()) {
				return ob;
			}
		}
		return null;
	}

	public static List<BreakableObject> getBreakableObjects(GameData gameData2) {
		List<BreakableObject> list = new ArrayList<>();
		for (GameObject ob : gameData2.getObjects()) {
			if (ob instanceof BreakableObject) {
				list.add((BreakableObject)ob);
			}
		}
		return list;
	}

}

package model.objects;

import java.util.ArrayList;

import model.Client;
import model.actions.Target;
import model.characters.CharacterDecorator;
import model.characters.InfectedCharacter;
import model.items.Weapon;

public class HiveObject extends BreakableObject {

	private boolean foundByHumanTeam = false;
	
	public HiveObject(String name) {
		super(name, 3.0);
	}
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Client whosAsking) {
		
		if (whosAsking.getCharacter() instanceof InfectedCharacter) {
			info.add(getName() + " (sensed)");
		} else if (isFound()){
			super.addYourselfToRoomInfo(info, whosAsking);
		}
	}

	public void setFound(boolean b) {
		foundByHumanTeam = b;
	}
	
	public boolean isFound() {
		return foundByHumanTeam;
	}

	@Override
	public boolean isTargetable() {
		return isFound();
	}


	
}

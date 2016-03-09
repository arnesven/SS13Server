package model.objects;

import java.util.ArrayList;

import model.Actor;
import model.Player;
import model.actions.Action;
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
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		
		if (isFound()){
			super.addYourselfToRoomInfo(info, whosAsking);
		} else if (whosAsking.getCharacter() instanceof InfectedCharacter) {
			info.add("o" + getName() + " (sensed)");
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

	@Override
	protected void addActions(Player cl, ArrayList<Action> at) {	}





	
}

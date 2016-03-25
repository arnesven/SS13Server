package model.objects;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InfectedCharacter;
import model.items.weapons.Weapon;
import model.map.Room;

public class HiveObject extends BreakableObject {

	private boolean foundByHumanTeam = false;
	
	public HiveObject(String name, Room pos) {
		super(name, 3.0, pos);
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
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {	}





	
}

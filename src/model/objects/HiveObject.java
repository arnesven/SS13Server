package model.objects;

import java.util.ArrayList;

import model.Client;
import model.characters.CharacterDecorator;
import model.characters.InfectedCharacter;

public class HiveObject extends GameObject {

	public HiveObject(String name) {
		super(name);
	}
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Client whosAsking) {
		
		if (whosAsking.getCharacter() instanceof CharacterDecorator ||
				whosAsking.getCharacter() instanceof InfectedCharacter) {
			info.add(getName() + " (sensed)");
		}
	}

}

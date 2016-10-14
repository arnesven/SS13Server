package model.objects.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.Room;

public class HiveObject extends BreakableObject {

	private boolean foundByHumanTeam = false;
	
	public HiveObject(String name, Room pos) {
		super(name, 3.0, pos);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("hive", "alien.png", 25);
        }
        return new Sprite("hive", "alien.png", 24);
    }

    @Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		
		if (isFound()){
			super.addYourselfToRoomInfo(info, whosAsking);
		} else if (whosAsking.isInfected()) {
            info.add(getSprite(whosAsking).getName() + "<img>" + this.getPublicName(whosAsking) + " (sensed)");

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

package model.items.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.Room;

public class Laptop extends UplinkItem {

	private Room jackRoom = null;
	
	public Laptop() {
		super("Laptop", 1.0);
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		return super.getFullName(whosAsking) + (isJackedIn()?" (jacked in)":"");
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		super.addYourActions(gameData, at, cl);
		at.add(new Action("Play games", SensoryLevel.OPERATE_DEVICE) {
			
			@Override
			public void setArguments(List<String> args, Actor performingClient) {	}
			
			@Override
			protected String getVerb(Actor whosAsking) {
					return "Played with laptop";
			}
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {}
		});
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("laptop", "computer2.png", 0, 18, 32, 32);
    }

    @Override
	public Laptop clone() {
		return new Laptop();
	}
	
	public boolean isJackedIn() {
		return jackRoom != null;
	}
	
	public void setJackRoom(Room r) {
		jackRoom = r;
	}
	
	public Room getJackRoom() {
		return jackRoom;
	}

	public void setNotJackedIn() {
		jackRoom = null;
	}

}

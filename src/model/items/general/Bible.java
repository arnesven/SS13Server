package model.items.general;

import java.util.ArrayList;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.SermonAction;
import model.characters.general.GameCharacter;
import model.characters.crew.ChaplainCharacter;
import model.characters.decorators.InstanceChecker;

public class Bible extends GameItem {

	private int godPoints = 0;

	public Bible() {
		super("Holy Bible", 1.0);
	}

	@Override
	public Bible clone() {
		return new Bible();
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (isAGodlyMan(cl.getCharacter())) {
			at.add(new SermonAction(this));
		}
	}

	private boolean isAGodlyMan(GameCharacter character) {
		InstanceChecker check = new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof ChaplainCharacter;
			}
		};
		return character.checkInstance(check);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bible", "bible.png", 0);
    }

    public void addGodPoints(int i) {
		this.godPoints  += i;
	}

	public int getGodPoints() {
		return godPoints;
	}
	

}

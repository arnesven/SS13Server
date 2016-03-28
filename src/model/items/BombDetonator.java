package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.*;

public class BombDetonator extends GameItem {

	private BombItem bomb;
	private boolean detonated = false;

	public BombDetonator(BombItem remoteBomb) {
		super("Detonator", 0.2);
		this.bomb = remoteBomb;
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (! detonated ) {
			at.add(new Action("Detonate " + bomb.getPublicName(cl),
					new SensoryLevel(VisualLevel.STEALTHY, 
							AudioLevel.ALMOST_QUIET, 
							OlfactoryLevel.UNSMELLABLE)) {

				@Override
				protected String getVerb(Actor performingClient) {
					return "used the detonator";
				}

				@Override
				public void setArguments(List<String> args, Actor p) { }

				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					performingClient.addTolastTurnInfo("You pressed the detonator.");
					bomb.explode(gameData, performingClient);
					BombDetonator.this.detonated = true;
				}
			});
		}
		
	}

	@Override
	public BombDetonator clone() {
		return new BombDetonator(this.bomb);
	}

}
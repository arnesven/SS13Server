package model.modes;

import java.util.ArrayList;

import util.MyRandom;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;

public class SecretGameMode extends GameMode {
	
	private GameMode innerMode;

	public SecretGameMode() {
		double d = MyRandom.nextDouble();
		if (d < 0.5) {
			this.innerMode = new HostGameMode();
		} else {
			this.innerMode = new TraitorGameMode();
		}
	}

	@Override
	protected void setUpOtherStuff(GameData gameData) {
		innerMode.setUpOtherStuff(gameData);
	}

	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
			GameData gameData) {
		innerMode.assignOtherRoles(listOfCharacters, gameData);
	}

	@Override
	public boolean gameOver(GameData gameData) {
		return innerMode.gameOver(gameData);
	}

	@Override
	public void setStartingLastTurnInfo() {
		innerMode.setStartingLastTurnInfo();
	}

	@Override
	public String getSummary(GameData gameData) {
		return innerMode.getSummary(gameData);
	}

	@Override
	protected void addAntagonistStartingMessage(Player c) {
		innerMode.addAntagonistStartingMessage(c);
	}

	@Override
	protected void addProtagonistStartingMessage(Player c) {
		c.addTolastTurnInfo("Just another day on SS13. What will happen today?");
	}

	@Override
	protected boolean isAntagonist(Player c) {
		return innerMode.isAntagonist(c);
	}

}

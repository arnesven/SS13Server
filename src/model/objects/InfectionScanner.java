package model.objects;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.items.GameItem;
import model.npcs.NPC;


public class InfectionScanner extends BreakableObject {

	private boolean loaded = false;
	
	public InfectionScanner() {
		super("BioScanner", 3.0);
	}
	
	@Override
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
		if (!loaded) {
			if (getChemicals(cl) != null) {
				at.add(new Action("Load BioScanner", false) {
					
					@Override
					public void setArguments(List<String> args) {	}
					
					@Override
					protected String getVerb() {
						return "loaded the BioScanner with chemicals";
					}
					
					@Override
					protected void execute(GameData gameData, Actor performingClient) {
						removeAChemicals(performingClient);
						loaded = true;
						performingClient.addTolastTurnInfo("You loaded the BioScanner with chemicals.");
					}
				});
			}
		} else {
			at.add(new Action("Activate BioScanner", false) {
				
				@Override
				public void setArguments(List<String> args) {}
				
				@Override
				protected String getVerb() {
					return "activated the BioScanner";
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (!loaded) {
						performingClient.addTolastTurnInfo("You failed to activate the BioScanner.");
					} else {
						loaded = false;
						giveBioScannerOutput(gameData, performingClient);
					}
				}
			});
		}
	}

	protected void giveBioScannerOutput(GameData gameData, Actor performingClient) {
		int infected = 0;
		for (Player p : gameData.getPlayersAsList()) {
			if (p.isInfected()) {
				infected++;
			}
		}
		for (NPC npc : gameData.getNPCs()) {
			if (npc.isInfected()) {
				infected++;
			}
		}
		int dieroll = MyRandom.nextInt(8);
		if (dieroll == 0) {
			infected--;
		} else if (dieroll == 7) {
			infected++;
		}
		infected = Math.max(infected, 0);
		performingClient.addTolastTurnInfo("BioScanner; " + infected + " infected crew members detected.");
	}

	protected void removeAChemicals(Actor performingClient) {
		performingClient.getItems().remove(getChemicals(performingClient));
	}

	private GameItem getChemicals(Actor cl) {
		for (GameItem gi : cl.getItems()) {
			if (gi.getName().equals("Chemicals")) {
				return gi;
			}
		}

		return null;
	}

}

package model.objects;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.npcs.NPC;


public class InfectionScanner extends ElectricalMachinery {

	private boolean loaded;

	public InfectionScanner() {
		super("BioScanner");
		loaded = false;
	}
	
	@Override
	public String getName() {
		if (!loaded) {
			return super.getName() + " (empty)";
		}
		return super.getName();
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		if (!loaded) {
			if (getChemicals(cl) != null) {
				at.add(new Action("Load BioScanner", SensoryLevel.OPERATE_DEVICE) {
					
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
						performingClient.addTolastTurnInfo("You loaded the BioScanner with chemicals. The machine is warming up...");
					}
				});
			}
		} else if (5 <=  gameData.getRound()) {
			
			at.add(new Action("Activate BioScanner", SensoryLevel.OPERATE_DEVICE) {
				
				@Override
				public void setArguments(List<String> args) {}
				
				@Override
				protected String getVerb() {
					return "activated the BioScanner";
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (!loaded || isInUse()) {
						performingClient.addTolastTurnInfo("You failed to activate the BioScanner.");
						
					} else {
						loaded = false;
						setInUse(true);
						giveBioScannerOutput(gameData, performingClient);
						gameData.executeAtEndOfRound(performingClient, this);
					}
				}
				
				@Override
				public void lateExecution(GameData gameData,
						Actor performingClient) {
					setInUse(false);
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

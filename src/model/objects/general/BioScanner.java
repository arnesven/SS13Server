package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.items.chemicals.Chemicals;

public class BioScanner extends ElectricalMachinery {

	private boolean loaded;

	public BioScanner(Room pos) {
		super("BioScanner", pos);
		loaded = false;
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bioscanner", "cryogenic.png", 104, this);
    }

    @Override
	public String getName() {
		if (!loaded) {
			return super.getName() + " (off)";
		}
		return super.getName();
	}
	
	@Override
	public void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (!loaded) {
	//		if (getChemicals(cl) != null) {
				at.add(new Action("Turn On BioScanner", SensoryLevel.OPERATE_DEVICE) {
					
					@Override
					public void setArguments(List<String> args, Actor p) {	}
					
					@Override
					protected String getVerb(Actor whosAsking) {
						return "turned the bioscanner on";
					}
					
					@Override
					protected void execute(GameData gameData, Actor performingClient) {
						removeAChemicals(performingClient);
						loaded = true;
						performingClient.addTolastTurnInfo("The BioScanner is warming up...");
					}
				});
			//}
		} else if (5 <=  gameData.getRound()) {
			
			at.add(new Action("Activate BioScanner", SensoryLevel.OPERATE_DEVICE) {
				
				@Override
				public void setArguments(List<String> args, Actor p) {}
				
				@Override
				protected String getVerb(Actor whosAsking) {
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
			if (gi instanceof Chemicals) {
				return gi;
			}
		}

		return null;
	}

}

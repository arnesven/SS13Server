package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.DoNothingAction;
import model.fancyframe.PDAOrderFancyFrame;
import model.items.TraitorItem;
import model.items.general.*;
import model.items.suits.Equipment;
import model.items.suits.SuperSuit;
import model.modes.TraitorGameMode;
import model.modes.objectives.TraitorObjective;
import model.actions.general.SensoryLevel;

public class UsePDAAction extends Action {

	private final GameData gameData;
	private TraitorGameMode traitorMode;
	private PDA pda;
	boolean order = true;
	private GameItem orderedItem;
	private boolean show = false;

	public UsePDAAction(TraitorGameMode traitorMode, PDA pda, GameData gameData) {
		super("Use PDA", SensoryLevel.OPERATE_DEVICE);
		this.traitorMode = traitorMode;
		this.gameData = gameData;
		this.pda = pda;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		if (orderedItem != null) {
			return "ordered " + orderedItem.getPublicName(whosAsking) + " with a PDA";
		}
		return "used a PDA";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
	    if (!GameItem.hasAnItemOfClass(performingClient, PDA.class)) {
	        performingClient.addTolastTurnInfo("You don't have a PDA!" + failed(gameData, performingClient));
        }

		if (!order) {
			if (traitorMode.getTraitors().size() > 1) {
				String others = "The hidden traitors are; ";
				for (Player p : traitorMode.getTraitors()) {
					if (performingClient != p) {
						others += p.getBaseName() + " (obj; \"" + traitorMode.getObjectives().get(p).getText() + "\")";
					}
				}
				performingClient.addTolastTurnInfo(others);
			} else {
				performingClient.addTolastTurnInfo("You are the only traitor.");
			}
		} else if (!show) {
            GameItem gi = null;
            if (orderedItem instanceof OrderBundle) {
                for (int i = ((OrderBundle) orderedItem).getNum(); i > 0; --i) {
                    gi = ((OrderBundle) orderedItem).getInnerItem().clone();
                    performingClient.addItem(gi, null);
                }
                performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) +
                        " appeared! You put it in your inventory.");
            } else if (orderedItem instanceof SuperSuit) {
                gi = orderedItem.clone();
                boolean couldSet = ((SuperSuit) gi).setAppearance(performingClient.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT),
						performingClient);
                if (couldSet) {
                    performingClient.putOnSuit((SuperSuit) gi);
                    performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) +
                            " appeared! You put it on.");
                } else {
                    performingClient.getCharacter().giveItem(gi, null);
                    performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) +
                            " appeared! You put it in your inventory.");
                }


            }else if (orderedItem instanceof LarcenyGloves) {
                gi = orderedItem.clone();
                performingClient.putOnSuit((LarcenyGloves)gi);
                performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) +
                        " appeared! You put them on.");
            } else {
                gi = orderedItem.clone();
                performingClient.addItem(gi, null);
                performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) +
                        " appeared! You put it in your inventory.");
            }
			pda.decrementUses(((TraitorItem)orderedItem).getTelecrystalCost());

			
			if (gi instanceof Locator) {
				setTargetFromObjective(gameData, performingClient, gi);
				performingClient.addTolastTurnInfo("The locator is set to your objective target.");
			}
		}

	}
	
	private void setTargetFromObjective(GameData gameData,
			Actor performingClient, GameItem gi) {
		TraitorObjective obj = traitorMode.getObjectives().get(performingClient);
		if (obj != null) {
			((Locator)gi).setTarget(obj.getLocatable());
		}
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption("Use PDA");
		opt.addOption("Show Objective (Free Action)");
		opt.addOption("Order Interface (Free Action)");
		opt.addOption("Request Info");
		if (pda.getTelecrystalsLeft() > 0) {
			ActionOption order = new ActionOption("Order Item (" + pda.getTelecrystalsLeft() + "TC left)");
			for (TraitorItem it : PDA.getOrderableItems()) {
				if (pda.getTelecrystalsLeft() >= it.getTelecrystalCost()) {
					order.addOption(((GameItem) it).getBaseName() + " (" + it.getTelecrystalCost() + "TC)");
				}
			}
			opt.addOption(order);
		} 
		
		return opt;
	}
	

	@Override
	public void setArguments(List<String> args, Actor p) {
		if (args.get(0).equals("Request Info")) {
			order = false;
		} else if (args.get(0).contains("Show Objective")) {
			if (p instanceof Player) {
				traitorMode.setAntagonistFancyFrame((Player) p);
				((Player) p).setNextAction(new DoNothingAction());
			}
			show = true;
		} else if (args.get(0).contains("Order Interface")) {
			if (p instanceof Player) {
				((Player) p).setFancyFrame(new PDAOrderFancyFrame((Player)p, pda, gameData));
				((Player) p).setNextAction(new DoNothingAction());
			}
			show = true;
		} else {
			for (TraitorItem it : PDA.getOrderableItems()) {
				if (args.get(1).contains(((GameItem)it).getBaseName())) {
					orderedItem = (GameItem)it;
				}
			}
			order = true;
		}
	}

	@Override
	public boolean doesSetPlayerReady() {
		return !show;
	}
}

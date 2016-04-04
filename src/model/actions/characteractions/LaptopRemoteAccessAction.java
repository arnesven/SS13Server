package model.actions.characteractions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.items.Laptop;
import model.objects.ElectricalMachinery;
import model.objects.GameObject;
import model.objects.RemotelyOperateable;

public class LaptopRemoteAccessAction extends Action {

	private Laptop pc;
	private List<GameObject> remotes;
	private String selectedAction;
	private List<String> args;

	public LaptopRemoteAccessAction(Laptop pc) {
		super("Remote Access", SensoryLevel.OPERATE_DEVICE);
		this.pc = pc;
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		remotes = getRemotes(gameData);
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (GameObject o : remotes) {
				for (Action a : getActionsForRemote(gameData, whosAsking, o)) {
					opt.addOption(a.getOptions(gameData, whosAsking));
				}
			
		}
		
		return opt;
	}
	


	@Override
	protected String getVerb(Actor whosAsking) {
		return "Played with laptop";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getItems().contains(pc)) {
		for (GameObject o : getRemotes(gameData)) {
			for (Action a : getActionsForRemote(gameData, performingClient, o)) {
				if (selectedAction.contains(a.getName())) {
					a.setArguments(args, performingClient);
					a.doTheAction(gameData, performingClient);
					break;
				}
			}
		}
		} else {
			performingClient.addTolastTurnInfo("What? the laptop is gone! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selectedAction = args.get(0);
		this.args = args.subList(1, args.size());
	}
	
	private List<GameObject> getRemotes(GameData gameData) {
		List<GameObject> remotes = new ArrayList<>();
		for (GameObject o : gameData.getObjects()) {
			if (o instanceof RemotelyOperateable) {
				if (!(o instanceof ElectricalMachinery) || 
						ElectricalMachinery.isPowered(gameData, (ElectricalMachinery)o)) {
					remotes.add(o);
				}
			}
		}
		return remotes;
	}
	
	private List<Action> getActionsForRemote(GameData gameData, Actor whosAsking, GameObject ob) {
		ArrayList<Action> at = new ArrayList<>();
		ob.addSpecificActionsFor(gameData, (Player)whosAsking, at);
		return at;
	}
	

}

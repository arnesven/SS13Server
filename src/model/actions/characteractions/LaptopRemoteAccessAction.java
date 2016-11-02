package model.actions.characteractions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.RemoteAccessAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.Laptop;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.RemotelyOperateable;

public class LaptopRemoteAccessAction extends RemoteAccessAction {

	private Laptop pc;


	public LaptopRemoteAccessAction(Laptop pc) {
		super(SensoryLevel.OPERATE_DEVICE);
		this.pc = pc;
	}


	@Override
	protected String getVerb(Actor whosAsking) {
		return "Played with laptop";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getItems().contains(pc)) {
		    super.execute(gameData, performingClient);
		} else {
			performingClient.addTolastTurnInfo("What? the laptop is gone! Your action failed.");
		}
	}


}

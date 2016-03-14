package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.BombItem;
import model.items.BoobyTrapBomb;
import model.objects.BoobyTrappedObject;
import model.objects.ElectricalMachinery;
import model.objects.GameObject;

public class RigBoobyTrapAction extends Action {

	private ElectricalMachinery selectedObject;
	private BombItem bomb;

	public RigBoobyTrapAction(BoobyTrapBomb boobyTrapBomb, 
								Actor performingClient) {
		super("Rig Booby Trap",
				SensoryLevel.PHYSICAL_ACTIVITY); 
		this.bomb = boobyTrapBomb;
		this.performer = performingClient;
	}


	@Override
	protected String getVerb() {
		return "tinkered with " + selectedObject.getName();
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("Rig Booby Trap{");
		for (GameObject obj : performer.getPosition().getObjects()) {
			if (obj instanceof ElectricalMachinery) {
				buf.append(obj.getName() + "{}");
			}
		}
		
		buf.append("}");
		return buf.toString();
	}

	@Override
	public void setArguments(List<String> args) {
		for (GameObject obj : performer.getPosition().getObjects()) {
			if (obj instanceof ElectricalMachinery) {
				if (obj.getName().equals(args.get(0))) {
					selectedObject = (ElectricalMachinery)obj;
				}
			}
		}
		
		
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.getItems().remove(bomb);
		performingClient.getPosition().getObjects().remove(selectedObject);
		performingClient.getPosition().addObject(new BoobyTrappedObject(selectedObject, bomb, 
				performingClient, performingClient.getPosition()));
		performingClient.addTolastTurnInfo("You booby-trapped the " + selectedObject.getName());

	}
}

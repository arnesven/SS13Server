package model.actions.characteractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.npcs.NPC;
import model.npcs.Trainable;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.AttackIfPossibleBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.FollowMeBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;
import util.MyRandom;

public class TrainNPCAction extends Action {

	private List<Trainable> trainables = new ArrayList<>();
	private Map<String, ActionBehavior> acts = new HashMap<>();
	private Map<String, MovementBehavior> movs = new HashMap<>();
	private ActionBehavior selectedAct;
	private MovementBehavior selectedMov;
	private Trainable target;
	
	public TrainNPCAction(Actor ap, GameData gameData) {
		super("Train", SensoryLevel.PHYSICAL_ACTIVITY);
		this.performer = ap;
		addTrainables(ap);
		addActionBehaviors();
		addMovementBehaviors(gameData);
	}

	private void addMovementBehaviors(GameData gameData) {
		movs.put("Run", new MeanderingMovement(0.9));
		movs.put("Stay", new MeanderingMovement(0.1));
		movs.put("Follow", new FollowMeBehavior(performer, gameData));

	}

	private void addActionBehaviors() {
		acts.put("Enrage", new AttackIfPossibleBehavior());
		acts.put("Calm", new DoNothingBehavior());
	}

	private void addTrainables(Actor ap) {
		for (Actor a : ap.getPosition().getActors()) {
			if (a instanceof Trainable && !a.isDead()) {
				trainables.add((Trainable)a);
			}
		}
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (Trainable t : trainables) {
			ActionOption op = new ActionOption(t.getPublicName());
			addTrainingAlternatives(op);
			opt.addOption(op);
		}
		return opt;
	}
	
	private void addTrainingAlternatives(ActionOption opt) {
		for (String a : acts.keySet()) {
			opt.addOption(a);
		}
		for (String a : movs.keySet()) {
			opt.addOption(a);
		}
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Trained "  + target.getPublicName();
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		NPC targetAsNPC = (NPC)target;
		if (targetAsNPC.isDead() || targetAsNPC.getPosition() != performingClient.getPosition()) {
			performingClient.addTolastTurnInfo(targetAsNPC.getPublicName() + " can no longer be trained. Your action failed.");
			return;
		}
		
		if (selectedAct != null) {
			targetAsNPC.setActionBehavior(selectedAct);
		} else {
			targetAsNPC.setMoveBehavior(selectedMov);
		}
		
		performingClient.addTolastTurnInfo("You trained " + targetAsNPC.getPublicName() + ".");
		if (MyRandom.nextDouble() < 0.5) {
			targetAsNPC.setHealth(targetAsNPC.getHealth() + 0.5);
			targetAsNPC.setMaxHealth(targetAsNPC.getMaxHealth() + 0.5);
			performingClient.addTolastTurnInfo(targetAsNPC.getPublicName() + " grew stronger!");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (Trainable t : trainables) {
			if (args.get(0).equals(t.getPublicName())) {
				target = t;
			}
		}
		
		selectedAct = null;
		selectedMov = null;
		if (acts.get(args.get(1)) != null) {
			selectedAct = acts.get(args.get(1));
		} else {
			selectedMov = movs.get(args.get(1));
		}
	}

}
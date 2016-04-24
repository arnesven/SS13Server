package model.items.general;

import java.util.ArrayList;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.actions.itemactions.DrawBloodAction;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.characters.decorators.CharacterDecorator;
import model.mutations.Mutation;
import model.mutations.MutationFactory;
import model.npcs.NPC;
import model.actions.itemactions.InjectionAction;

public class Syringe extends GameItem {

	private boolean filled = false;
	private GameCharacter bloodFrom;
	private Mutation mutation = null;
	
	public Syringe() {
		super("Syringe", 0.1);
	}
	
	@Override
    public String getFullName(Actor whosAsking) {
        return  super.getFullName(whosAsking) + (filled ? " (filled)" : "");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("syringe", "items.png", 55);
    }

    public Mutation getMutation() {
		if (!filled) {
			throw new IllegalStateException("Tried to get mutation but syringe is not filled!");
		}
		return mutation;
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (!filled) {
			TargetingAction action = new DrawBloodAction(cl);
			if (action.getOptions(gameData, cl).numberOfSuboptions() > 0) {
				at.add(action);
			}
		} else {
			TargetingAction action = new InjectionAction(cl);
			if (action.getOptions(gameData, cl).numberOfSuboptions() > 0) {
				at.add(action);
			}
		}
	}
	
	@Override
	public GameItem clone() {
		return new Syringe();
	}

	public void empty() {
		this.filled = false;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void setBloodFrom(Actor target, GameData gameData) {
		this.bloodFrom = target.getCharacter().clone();
		this.filled = true;
		this.mutation = MutationFactory.getActorMutation(bloodFrom, gameData);
	}
	
	public CharacterDecorator getMutationDecorator(Actor forWhom) {
		if (!filled) {
			throw new IllegalStateException("Cannot get mutation, syringe not filled!");
		}
		return this.mutation.getDecorator(forWhom);
	}

	public static boolean hasBloodToDraw(Target target2) {
		if (target2 instanceof Player || target2 instanceof NPC) {
			Actor targetAsActor = (Actor)target2;
            return !(targetAsActor.getCharacter() instanceof RobotCharacter);
        }
		return false;
	}

	public static Syringe findSyringe(Actor performingClient) {
		Syringe syringe = null;
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof Syringe) {
				syringe = (Syringe)it;
			}
		}
		return syringe;
	}

	public void setMutation(Mutation randomMutation) {
		this.mutation = randomMutation;
		
	}



	



}

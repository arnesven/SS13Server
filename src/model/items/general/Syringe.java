package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.actions.itemactions.DrawBloodAction;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.general.RobotCharacter;
import model.characters.decorators.CharacterDecorator;
import model.mutations.Mutation;
import model.mutations.MutationFactory;
import model.npcs.NPC;
import model.actions.itemactions.InjectionAction;

public class Syringe extends GameItem {

	private boolean filled = false;
	private GameCharacter bloodFrom;
	private Actor originalActor;
	private Mutation mutation = null;
    private boolean infected;

    public Syringe() {
		super("Syringe", 0.1, 14);
	}
	
	@Override
    public String getFullName(Actor whosAsking) {
        return  super.getFullName(whosAsking) + (filled ? " (filled)" : "");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("syringe", "items.png", 55, this);
    }

    public Mutation getMutation() {
		if (!filled) {
			throw new IllegalStateException("Tried to get mutation but syringe is not filled!");
		}
		return mutation;
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
    	super.addYourActions(gameData, at, cl);
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

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setBloodFrom(Actor target, GameData gameData) {
        this.originalActor = target;
		this.bloodFrom = target.getCharacter().clone();
		this.filled = true;
		this.mutation = MutationFactory.getActorMutation(bloodFrom, gameData);
        this.infected = target.isInfected();
        if (target.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ParasiteCharacter)) {
            this.infected = true;
        }
	}
	
	public CharacterDecorator getMutationDecorator(Actor forWhom) {
		if (!filled) {
			throw new IllegalStateException("Cannot get mutation, syringe not filled!");
		}
		return this.mutation.getDecorator(forWhom);
	}

    public CharacterDecorator getMutationDecorator(Actor targetAsActor, Actor performingClient) {
        return getMutationDecorator(targetAsActor);
    }

	public static boolean hasBloodToDraw(Target target2) {
		if (target2 instanceof Player || target2 instanceof NPC) {
			Actor targetAsActor = (Actor)target2;
            return !(targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RobotCharacter))
                    && targetAsActor.getCharacter().isVisible();
        }
		return false;
	}

	public static Syringe findSyringe(Actor performingClient, boolean filled) {
		Syringe syringe = null;
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof Syringe) {
                if (filled) {
                    if (((Syringe) it).isFilled()) {
                        syringe = (Syringe) it;
                    }
                } else {
                    if (!((Syringe) it).isFilled()) {
                        syringe = (Syringe) it;
                    }
                }
			}
		}
		return syringe;
	}



	public void setMutation(Mutation randomMutation) {
		this.mutation = randomMutation;
		
	}


    public boolean isInfected() {
        return infected;
    }

    public Actor getOriginalActor() {
        return originalActor;
    }


}

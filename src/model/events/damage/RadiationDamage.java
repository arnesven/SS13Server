package model.events.damage;

import model.Actor;
import model.GameData;
import model.Target;
import model.mutations.Mutation;
import model.mutations.MutationFactory;
import sounds.Sound;
import util.Logger;
import util.MyRandom;

public class RadiationDamage extends DamagerImpl {

    private static final double CHANCE_OF_RANDOM_MUTATION = 0.05;
    private final GameData gameData;
    private double damage;
    

	public RadiationDamage(double damage, GameData gameData) {
		this.damage = damage;
        this.gameData = gameData;
	}

	@Override
	public String getText() {
		return "You feel sick...";
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return true;
	}

	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public String getName() {
		return "Acute radiation sickness";
	}

    @Override
    public void doDamageOnMe(Target target) {
        super.doDamageOnMe(target);
        
        if (target instanceof  Actor) {
            Actor a = (Actor)target;
            if (MyRandom.nextDouble() < CHANCE_OF_RANDOM_MUTATION) {
                Logger.log(Logger.INTERESTING, a.getBaseName() + " was randomly mutated by radiation!");
                Mutation mut = MutationFactory.getRandomMutation(gameData);
                a.setCharacter(mut.getDecorator(a));
            }
        }
    }

	@Override
	public boolean hasRealSound() {
		return true;
	}

	@Override
	public Sound getRealSound() {
		return new Sound("radioactivity");
	}
}

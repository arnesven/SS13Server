package model.items.weapons;

import graphics.ExtraEffect;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.characters.decorators.BloodSplotchAnimationDecorator;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;
import model.items.HandheldItem;
import model.items.general.GameItem;
import model.map.rooms.Room;
import sounds.GeneralWeaponSound;
import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;


public abstract class Weapon extends GameItem implements HandheldItem {

	//public static final Weapon FISTS      = new UnarmedAttack();
    public static final Weapon TENTACLE   = new Tentacle();
    public static final Weapon STEEL_PROD = new SteelProd();
    public static final Weapon TEETH      = new Teeth();
    public static final Weapon TUSKS      = new Tusks();
    public static final Weapon FLYING_CREDIT = new FlyingCredit();
    public static final Weapon HUGE_CLAWS = new HugeClaws();
    public static Weapon CLAWS            = new Claws();
    public static Weapon BEAR_CLAWS       = new BearClaws();

    private static final double BOTCH_CHANCE = 0.02;


    private double criticalChance = 0.05;
    private double hitChance;
	private double damage;
	private boolean makesBang;
    private double lastRoll;
    private boolean attackOfOpportunity;

    public Weapon(String string, double hitChance, double damage,
                  boolean bang, double weight, boolean attackOfOp, int cost) {
        super(string, weight, cost);
        this.hitChance = hitChance;
        this.damage = damage;
        makesBang = bang;
        attackOfOpportunity = attackOfOp;
    }

    public Weapon(String string, double hitChance, double damage,
				  boolean bang, double weight, int cost) {
        this(string, hitChance, damage, bang, weight, false, cost);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("weapon", "gun.png", 0, this);
    }

    public boolean isAttackSuccessful(double modifier) {
        lastRoll = MyRandom.nextDouble()*modifier;
        return lastRoll < getHitChance();
	}


    public boolean isAttackSuccessfulOnImmobileTarget() {
        lastRoll = MyRandom.nextDouble();
        return lastRoll < getHitChance()*1.25;
    }

	public double getHitChance() {
		return hitChance;
	}
	
	public double getDamage() {
		return damage;
	}

	public String getSuccessfulMessage() {
		return "attack";
	}

	public SensoryLevel getSensedAs() {
		if (!makesBang) {
			return SensoryLevel.PHYSICAL_ACTIVITY;
		}
		
		return new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
								AudioLevel.VERY_LOUD, 
								OlfactoryLevel.UNSMELLABLE);
	}

	public boolean isReadyToUse() {
		return true;
	}



	protected boolean makesBang() {
		return makesBang;
	}

	/**
	 * This method is called when the weapon has
	 * successfully hit the target.
	 * @param performingClient
	 */
	protected void usedOnBy(Target target, Actor performingClient,
			GameData gameData) {
	    makeHoldInHand(performingClient);
    }

    protected boolean hasExtraEffect() {
	    return false;
    }

	protected AnimatedSprite getExtraEffectSprite() {
	    return null;
    }


    protected void checkHazard(Actor performingClient, GameData gameData) { }

    protected void checkOnlyMissHazard(Actor performingClient, GameData gameData, Target originalTarget) {
	    if (MyRandom.nextDouble() < BOTCH_CHANCE) {
            List<Target> otherTargets = new ArrayList<>();
            otherTargets.addAll(performingClient.getPosition().getTargets(gameData));
            otherTargets.remove(originalTarget);
            otherTargets.remove(performingClient);
            if (otherTargets.size() > 0) {
                Target newTarge = MyRandom.sample(otherTargets);
                performingClient.addTolastTurnInfo("You botched your attack!");
                doAttack(performingClient, newTarge, gameData);
            }
        }

    }


	public void setDamage(double d) {
		this.damage = d;
	}


    public boolean wasCriticalHit() {
        return lastRoll <= criticalChance;
    }

    protected void setCriticalChance(double newChance) {
        this.criticalChance = newChance;
    }

    public void dealDamageOnMe(Actor actor) {
        actor.subtractFromHealth(this.getDamage());
    }

    public void dealCriticalDamageOnMe(Actor actor) {
        actor.subtractFromHealth((this.getDamage()*2));
    }


    public boolean givesAttackOfOpportunity() {
        return attackOfOpportunity;
    }

    public boolean doAttack(Actor performingClient, Target target, GameData gameData) {
        boolean success = target.beAttackedBy(performingClient, this, gameData);
        if (success) {
            usedOnBy(target, performingClient, gameData);
        } else {
            checkOnlyMissHazard(performingClient, gameData, target);
        }
        applyExtraEffectIfAble(performingClient, target, gameData);
        checkHazard(performingClient, gameData);
        return success;
    }

    protected void applyExtraEffectIfAble(Actor performingClient, Target target, GameData gameData) {
        if (hasExtraEffect()) {
            if (target instanceof SpriteObject && performingClient instanceof SpriteObject) {
                AnimatedSprite beamSprite = getExtraEffectSprite();
                ExtraEffect.makeExtraEffectForAllInRoom(performingClient, (SpriteObject)target, beamSprite,
                        beamSprite.getFrames(), beamSprite.isLooping());
            }
        }
    }

    public String getCriticalMessage() {
        List<String> messages = new ArrayList<>();
        messages.add("Took out an eye");
        messages.add("Cut an artery");
        messages.add("Broke a nose");
        messages.add("Broke a rib");
        messages.add("Hit the skull");
        messages.add("Splintered a jaw");
        messages.add("Pierced a lung");
        messages.add("Struck the groin");
        messages.add("Some guts came out");
        messages.add("Blood shoots out");
        messages.add("Broke an arm");
        messages.add("Hit the head");
        messages.add("Crushed an elbow");
        messages.add("Crushed a hand");
        messages.add("Right in the mouth");
        return MyRandom.sample(messages);
    }


    public Sprite getHandHeldSprite() {
        return Sprite.blankSprite();
	    //return new Sprite("weaponinhand", "items_righthand.png", 0, 4, this);
    }

    public void setHitChance(double hitChance) {
        this.hitChance = hitChance;
    }

    public boolean hasRealSound() {
        return true;
    }

    public Sound getRealSound() {
        return new GeneralWeaponSound();
    }

    public double getAmpChance() {
        return 0.25;
    }

    public void applyAnimation(Actor actor, Actor performingClient, GameData gameData) {
        if (this instanceof PhysicalWeapon && (actor.isHuman() || actor.isAnimal())) {
            if (!actor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof BloodSplotchAnimationDecorator)) {
                actor.setCharacter(new BloodSplotchAnimationDecorator(actor.getCharacter(), gameData));
            }
        }
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
	    return "<b>Hit Chance:</b> " + hitChance + ", <b>Damage:</b> " + damage + "<br/>" +
                (givesAttackOfOpportunity()?"<i>Gives attacks of opportunity!</i><br/>":"") +
                (criticalChance!=0.05?("<b>Critical Chance:</b> " + (int)(criticalChance*100) + "%"):"");
    }


    public double doWallDamage(GameData gameData, Actor performingClient) {
	    checkHazard(performingClient, gameData);
	    return damage;
    }

    public String getWallDamageText() {
        return "Sounds like somebody is banging on the wall!";
    }

    public boolean hasMissSound() {
        return false;
    }

    public Sound getMissSound() {
	    if (hasMissSound()) {
	        return new Sound("punchmiss");
        }
        return null;
    }

    public boolean isMeleeWeapon() {
        return false;
    }
}

package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.NameChangeDecorator;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.ExplosiveDamage;
import model.events.damage.FireDamage;
import model.events.damage.PhysicalDamage;
import model.items.weapons.Weapon;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import util.MyRandom;

/**
 * Created by erini02 on 09/09/17.
 */
public class Rapido extends FootGear {

    private static final double CRASH_CHANCE = 0.25;
    private CharacterDecorator cd;
    private double rapidoHealth = 2.0;

    public Rapido() {
        super("Rapido", 65.0, 695);
    }

    @Override
    public SuitItem clone() {
        return new Rapido();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new AlterMovement(actionPerformer.getCharacter(),
				"Rapido", false, 3));
        cd = new NameChangeDecorator(actionPerformer.getCharacter(),
                actionPerformer.getPublicName() + " on Rapido");
        actionPerformer.setCharacter(cd);
        actionPerformer.setCharacter(new RapidoHealthTracker(actionPerformer.getCharacter()));

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("rapido", "vehicles.png", 2, 6, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("rapido", "vehicles.png", 3, 6, this);
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AlterMovement)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof AlterMovement);
        }
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc == cd)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc == cd);
        }
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RapidoHealthTracker)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof RapidoHealthTracker);
        }
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public boolean canBePickedUp() {
        return false;
    }

    private class RapidoHealthTracker extends CharacterDecorator {

        private Room fromRoom;

        public RapidoHealthTracker(GameCharacter character) {
            super(character, "rapidohelathtracker");
        }

        @Override
        public void beExposedTo(Actor something, Damager damager) {
            super.beExposedTo(something, damager);
            if (damager instanceof FireDamage || damager instanceof ExplosiveDamage) {
                rapidoTakeDamage(damager.getDamage(), getActor());
            }
        }

        @Override
        public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
            boolean res = super.beAttackedBy(performingClient, weapon);
            if (MyRandom.nextDouble() < 0.5) {
                rapidoTakeDamage(weapon.getDamage(), getActor());
            }
            return res;
        }

        @Override
        public void doBeforeMovement(GameData gameData) {
            super.doBeforeMovement(gameData);
            fromRoom = getActor().getPosition();
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);

            int dist = GameMap.shortestDistance(fromRoom, getActor().getPosition());
            if (dist == 3) {
                if (MyRandom.nextDouble() < CRASH_CHANCE) {
                    crashIntoStuff(gameData, getActor());
                }
            }
        }
    }

    private void crashIntoStuff(GameData gameData, Actor actor) {
        Target target = null;
        if (actor.getPosition().getTargets(gameData).size() > 1) {
            do {
                target = MyRandom.sample(actor.getPosition().getTargets(gameData));
            } while (target == actor);


        } else {
            actor.addTolastTurnInfo("You crashed into a wall with the rapido!");

        }

        if (target != null) {
            if (target instanceof Actor) {
                ((Actor) target).addTolastTurnInfo(actor.getPublicName() + " crashed into you!");
            }
            target.beExposedTo(actor, new PhysicalDamage(this), gameData);
            actor.addTolastTurnInfo("You crashed into " + target.getName() + "!");
        }
        rapidoTakeDamage(1.0, actor);
        actor.getCharacter().beExposedTo(actor, new PhysicalDamage(this));

    }

    private void rapidoTakeDamage(double damage, Actor victim) {
        double healthBefore = rapidoHealth;
        rapidoHealth = Math.max(0.0, rapidoHealth-damage);
        if (rapidoHealth == 0.0 && healthBefore != 0.0) {
           eject(victim);
        }
    }

    private void eject(Actor victim) {
        if (victim.getCharacter().getEquipment().getEquipmentForSlot(Equipment.FEET_SLOT) == this) {
            victim.getCharacter().getEquipment().removeEquipmentForSlot(Equipment.FEET_SLOT);
        }
        victim.getPosition().addObject(new BrokenRapido(victim.getPosition()));
    }

    private class BrokenRapido extends GameObject {


        public BrokenRapido(Room r) {
            super("Broken Rapido", r);
        }


        @Override
        public Sprite getSprite(Player whosAsking) {
             return new Sprite("rapidobroken", "vehicles.png", 4, 6, this);
        }
    }
}

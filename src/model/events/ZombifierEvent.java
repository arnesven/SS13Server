package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.decorators.HuskDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.ZombieDecorator;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import model.npcs.ZombieNPC;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.AttackIfPossibleBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;
import util.Logger;
import util.MyRandom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erini02 on 03/05/16.
 */
public class ZombifierEvent extends Event {
    private static final double TURNING_CHANCE_STAGE2 = 0.75;
    private static final double TURNING_CHANCE_STAGE1 = 0.2;
    private Set<Actor> turningZombie = new HashSet<>();
    private Set<Actor> alreadyTurned = new HashSet<>();

    @Override
    public void apply(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (a.isDead() && isZombiefiable(a)) {
                if (turningZombie.contains(a) && MyRandom.nextDouble() < TURNING_CHANCE_STAGE2) {
                    turnIntoZombie(a, gameData, alreadyTurned);
                    Logger.log(Logger.INTERESTING, a.getCharacter().getBaseName() + " turned into a zombie.");

                    turningZombie.remove(a);
                    alreadyTurned.add(a);
                } else if (!alreadyTurned.contains(a) && MyRandom.nextDouble() < TURNING_CHANCE_STAGE1) {
                    turningZombie.add(a);
                    a.setCharacter(new HuskDecorator(a.getCharacter()));
                }
            }
        }
    }

    public static void turnIntoZombie(Actor a, GameData gameData, Set<Actor> alreadyTurned) {
        try {
            MovementBehavior move = new MeanderingMovement(0.5);
            AttackIfPossibleBehavior atk = new AttackAllActorsButNotTheseClasses(List.of(ZombieDecorator.class));
            if (a instanceof Player) {
                a.getPosition().removeActor(a);
                NPC npc = new ZombieNPC(new ZombieDecorator(a.getCharacter().clone()), move, atk, a.getPosition());
                gameData.addNPC(npc);
                npc.moveIntoRoom(npc.getPosition());
                alreadyTurned.add(npc);
                npc.setHealth(1.5);
            } else {
                //NPC
                a.setCharacter(new ZombieDecorator(a.getCharacter()));
                ((NPC)a).setMoveBehavior(move);
                ((NPC)a).setActionBehavior(atk);
                ((NPC) a).setHealth(1.5);
            }
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "What no player to remove?!");
        }
    }

    public  boolean isZombiefiable(Actor a) {
        if (!isZombie(a)) {
           return isHuman(a) || isAnimal(a);
        }
        return false;
    }

    private boolean isAnimal(Actor a) {
        return a.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof AnimalCharacter;
            }
        });
    }

    private boolean isHuman(Actor a) {
        return a.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof HumanCharacter;
            }
        });
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }

    public boolean isZombie(Actor value) {
        return alreadyTurned.contains(value);
    }

    public Set<Actor> getZombies() {
        return alreadyTurned;
    }
}

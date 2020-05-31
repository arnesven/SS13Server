package model.characters.special;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.items.CosmicMonolith;
import model.items.weapons.PulseRifle;
import model.items.weapons.Weapon;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.objects.monolith.DoomsdayMachine;

public class DoomsdayMachineCharacter extends RobotCharacter {
    private final CosmicMonolith monolith;
    private final DoomsdayMachineWeapon weapon;

    public DoomsdayMachineCharacter(CosmicMonolith cm, int id) {
        super("Cosmic Monolith", id, 21.5);
        this.monolith = cm;
        this.weapon = new DoomsdayMachineWeapon();
    }

    @Override
    protected Sprite getNormalSprite(Actor whosAsking) {
        return monolith.getSprite(whosAsking);
    }

    @Override
    protected Sprite getBrokenSprite(Actor whosAsking) {
        return monolith.getSprite(whosAsking);
    }

    @Override
    public Weapon getDefaultWeapon() {
        return weapon;
    }

    private class DoomsdayMachineWeapon extends PulseRifle {
        public DoomsdayMachineWeapon() {
            setName("Energy Beam");
        }

        @Override
        protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
            super.usedOnBy(target, performingClient, gameData);
            if (getShots() <= 0) {
                if (performingClient instanceof NPC) {
                    ((NPC)performingClient).setActionBehavior(new DoNothingBehavior());
                    ((NPC)performingClient).setMoveBehavior(new MeanderingMovement(0.0));
                }
            }
        }
    }
}

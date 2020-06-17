package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.itemactions.CancelAction;
import model.characters.decorators.SpriteOverlayDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingMovement;
import util.HTMLText;
import util.MyRandom;


public class IceStaff extends StaffWeapon {
    private static double FREEZE_CHANCE = 0.45;

    public IceStaff() {
        super("Ice Staff", false);
        setDamage(0.5);
    }

    @Override
    public GameItem clone() {
        return new IceStaff();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("icestaff", "weapons2.png", 6, 27, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A powerful magic staff that, apart from shooting freezing dabs of ice at your adversary, also has" +
                " the chance (" + (int)(FREEZE_CHANCE*100) +"%) of freezing them in a block of ice. " +
                "Frozen characters normally thaw after a couple of turns.";
    }

    @Override
    public void usedOnBy(Target target, Actor performingClient,
                         final GameData gameData) {
        if (target instanceof Actor) {
            if (MyRandom.nextDouble() < FREEZE_CHANCE) {
                final Actor victim = (Actor) target;
                victim.setCharacter(new FrozenInIceDecorator(victim.getCharacter(), gameData.getRound()));

                cancelAction(gameData, victim);
                performingClient.addTolastTurnInfo("You froze " + target.getName() + " in a block of ice!");
                victim.addTolastTurnInfo(HTMLText.makeText("red", "You were frozen in a block of ice!"));
            }
        }
    }

    private void cancelAction(GameData gameData, Actor victim) {
        if (victim instanceof Player) {
            ((Player)victim).setNextAction(new CancelAction());
        } else if (victim instanceof NPC) {
            ((NPC)victim).setCancelled(true);
            ((NPC) victim).setMoveBehavior(new MeanderingMovement(0.0));
        }
    }

    private class FrozenInIceDecorator extends SpriteOverlayDecorator {
        private final int roundSet;

        public FrozenInIceDecorator(GameCharacter character, int roundSet) {
            super(character, "frozen", new Sprite("blockofice", "wizardstuff.png", 2, null));
            this.roundSet = roundSet;
        }

        @Override
        public String getFullName() {
            return super.getFullName() + " (frozen)";
        }

        @Override
        public boolean getsActions() {
            return false;
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            int turnsPassed = roundSet - gameData.getRound();
            if (turnsPassed > 0) {
                if (MyRandom.nextDouble() > (1.0 / turnsPassed)) {
                    getActor().removeInstance((GameCharacter gc) -> gc == this);
                    getActor().addTolastTurnInfo("You have thawed out of the ice.");
                }
            }


        }
    }
}

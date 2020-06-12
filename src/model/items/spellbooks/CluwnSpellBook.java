package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.*;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PoofOfSmokeAnimationDecorator;
import model.characters.decorators.TalkingDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import sounds.FartSound;
import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class CluwnSpellBook extends SpellBook {
    private static final int CLUWN_DURATION_ROUNDS = 8;
    private static final double CLUWN_SPAT_CHANCE = 0.33;

    public CluwnSpellBook() {
        super("Cluwn", 12);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("cluwnspellbook", "wizardstuff.png", 2, 2, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof Actor) {
            Actor victim = (Actor)target;
            victim.setCharacter(new CluwnAppearanceDecorator(victim.getCharacter(), gameData.getRound()));
            if (victim instanceof NPC) {
                ((NPC) victim).setActionBehavior(new CluwnNPCBehavior(gameData.getRound(), (NPC)victim));
            } else if (victim instanceof Player) {
                victim.setCharacter(new CluwnActionCrazynessDecorator(victim.getCharacter(), gameData.getRound()));
            }
        }
    }

    public void removeCluwnDecorators(Actor victim) {
        if (victim.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CluwnAppearanceDecorator)) {
            victim.removeInstance((GameCharacter gc) -> gc instanceof CluwnAppearanceDecorator);
        }
        if (victim.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CluwnActionCrazynessDecorator)) {
            victim.removeInstance((GameCharacter gc) -> gc instanceof CluwnActionCrazynessDecorator);
        }
    }

    @Override
    public String getSpellName() {
        return "Cluwn";
    }

    @Override
    public String getMagicWords() {
        return "Brackium Emendo!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastCluwnSpellAction(this, forWhom);
    }

    @Override
    protected String getSpellDescription() {
        return "A spell which turns the victim into a mad irish clown - or \"cluwn\". Cluwns laugh, fart and spasm uncontrollably. " +
                "The effects normally wear off after 8 turns.";
    }

    @Override
    public GameItem clone() {
        return new CluwnSpellBook();
    }

    private class CluwnAppearanceDecorator extends CharacterDecorator {
        public CluwnAppearanceDecorator(GameCharacter character, int round) {
            super(character, "Cluwnappearance");
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            List<Sprite> sprs = new ArrayList<>();
            sprs.add(getNakedSprite());
            sprs.add(new Sprite("cluwnsuit", "uniform2.png", 27, 32, null));
            sprs.add(new Sprite("cluwnmask", "mask.png", 6, 18, null));
            sprs.add(new Sprite("cluwnshoes", "feet.png", 75, null));
            Sprite total = new Sprite("cluwntotal" + getNakedSprite().getName(), "human.png", 0, sprs, getActor());
            if (isDead()) {
                total.setRotation(90);
            }
            return total;
        }


    }

    private class CluwnNPCBehavior implements ActionBehavior {
        private final int roundSet;
        private final NPC npc;
        private final ActionBehavior oldBehavior;

        public CluwnNPCBehavior(int round, NPC victim) {
            this.roundSet = round;
            this.npc = victim;
            this.oldBehavior = victim.getActionBehavior();
        }

        @Override
        public void act(Actor npc, GameData gameData) {
            if (gameData.getRound() > roundSet + CLUWN_DURATION_ROUNDS) {
                ((NPC)npc).setActionBehavior(oldBehavior);
                removeCluwnDecorators(npc);
                oldBehavior.act(npc, gameData);
            } else if (MyRandom.nextDouble() < CLUWN_SPAT_CHANCE) {
                CluwnAction cla = new CluwnAction();
                cla.doTheAction(gameData, npc);
            } else {
                oldBehavior.act(npc, gameData);
            }
        }
    }

    private class CluwnActionCrazynessDecorator extends CharacterDecorator {
        private final int roundSet;

        public CluwnActionCrazynessDecorator(GameCharacter character, int round) {
            super(character, "Cluwn Craziness");
            this.roundSet = round;
        }

        @Override
        public void doAction(Action nextAction, GameData gameData) {
            if (MyRandom.nextDouble() < CLUWN_SPAT_CHANCE) {
                Action cluwnAction = new CluwnAction();
                super.doAction(cluwnAction, gameData);
            } else {
                super.doAction(nextAction, gameData);
            }
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (gameData.getRound() > roundSet + CLUWN_DURATION_ROUNDS) {
                removeCluwnDecorators(getActor());
            }
        }
    }

    private class CluwnAction extends Action {

        private final int type;
        private final String randLaugh;
        private final String randFart;

        public CluwnAction() {
            super("Cluwn Action", SensoryLevel.SPEECH);
            this.type = MyRandom.nextInt(3);
            this.randLaugh = MyRandom.sample(List.of("HAHAHAHAHAH hohoh hihihihi", "Hehehehehehhe hihi",
                    "HOhoHOhoHO hehe", "HIHIHIHIH IIIIIIH",
                    "HA-A-AHAHA", "MUA-HAHAHA", "heheheh HEHEHEH heheheh"));
            this.randFart = MyRandom.sample(List.of("ugh, the stench!", "yuck, smells like bad eggs!", "goodness, what did you eat?",
                    "uuuh, you feel sick.", "how disgusting!", "GOD DAMN!"));
        }

        @Override
        public boolean hasRealSound() {
            return type == 0 || type == 1;
        }

        @Override
        public Sound getRealSound() {
            if (type == 0) {
                return new Sound("evil_laugh_" + (MyRandom.nextInt(3) + 1));
            } else if (type == 1) {
                return new FartSound();
            }
            return null;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            if (type == 0) {
                return "laughed hysterically: \"" + randLaugh + "!!!\"";
            } else if (type == 1) {
                return "farted loudly - " + randFart;
            }
            return "trembled violently";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (type == 0) {
                performingClient.addTolastTurnInfo("You said: \"" + randFart + "\"");
                Talking.decorateWithTalk(gameData, performingClient);
            } else if (type == 1) {
                performingClient.addTolastTurnInfo("You farted loudly, " + randFart);
                performingClient.setCharacter(new PoofOfSmokeAnimationDecorator(performingClient.getCharacter(), gameData, true));
            } else {
                performingClient.addTolastTurnInfo("You trembled violently");
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class CastCluwnSpellAction extends CastSpellAction {
        public CastCluwnSpellAction(CluwnSpellBook cluwnSpellBook, Actor forWhom) {
            super(cluwnSpellBook, forWhom);
            addTarget(forWhom.getAsTarget());
        }

        @Override
        protected boolean canBeTargetedBySpell(Target target2) {
            return target2 instanceof Actor && !target2.isDead() && ((Actor) target2).isHuman();
        }

    }
}

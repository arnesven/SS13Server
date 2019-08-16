package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class AutoAttack extends MovePower {
    public AutoAttack() {
        super("Ambush Entering Character");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        performingClient.setCharacter(new AttackSomeoneWhoEntered(performingClient.getCharacter(), moveData));
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("autoattackbutton", "buttons2.png", 3, 7, this);
    }

    private class AttackSomeoneWhoEntered extends CharacterDecorator {
        private final MovementData moveData;

        public AttackSomeoneWhoEntered(GameCharacter character, MovementData moveData) {
            super(character, "attacksomeonewhoentered");
            this.moveData = moveData;
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);
            List<Actor> candidates = new ArrayList<>();
            candidates.addAll(getPosition().getActors());
            candidates.removeIf((Actor a) -> moveData.getLastPosition(a) == getPosition());
            if (candidates.size() > 0) {
                Actor target = MyRandom.sample(candidates);
                target.addTolastTurnInfo(getActor().getPublicName() + " ambushed you!");
                getActor().addTolastTurnInfo("You ambushed " + target.getPublicName() + "!");
                AttackAction atk = new AttackAction(getActor());
                List<String> args = new ArrayList<>();
                args.add(target.getPublicName());
                Weapon w = getWeapon(getActor());
                atk.addWithWhat(w);
                args.add(w.getFullName(getActor()));
                atk.setArguments(args, getActor());
                atk.doTheAction(gameData, getActor());
            }

            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }

        private Weapon getWeapon(Actor actor) {
            for (GameItem it : actor.getItems()) {
                if (it instanceof Weapon) {
                    if (((Weapon)it).isReadyToUse()) {
                        return (Weapon)it;
                    }
                }
            }

            return actor.getCharacter().getDefaultWeapon();
        }
    }
}

package model.programs;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.RobotCharacter;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MoveTowardsClosestActorMovement;
import model.npcs.robots.RobotNPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erini02 on 14/04/16.
 */
public class BotProgram implements Serializable {
    private final Sprite sprite;
    private MovementBehavior moveBehave;
    private ActionBehavior actBehave;
    private String name;

    public BotProgram(String name, MovementBehavior mov, ActionBehavior act, Sprite sprite) {
        this.name = name;
        this.moveBehave = mov;
        this.actBehave = act;
        this.sprite = sprite;
    }

    //public BotProgram(String name, MovementBehavior mov, ActionBehavior act) {
    //    this(name, mov, act, null);
    //}

    public String getName() {
        return this.name;
    }

    public void loadInto(RobotNPC selectedBot, Actor whosAsking) {
        selectedBot.setMoveBehavior(this.moveBehave);
        selectedBot.setActionBehavior(this.actBehave);
        if (sprite != null) {

            selectedBot.setCharacter(new CharacterDecorator(selectedBot.getCharacter(), "sprite change") {
                private Sprite sp = new Sprite(sprite.getName() + getName(), sprite.getMap(), sprite.getColumn(), sprite.getRow(), selectedBot);
                @Override
                public Sprite getSprite(Actor whosAsking) {
                    if (!sp.isRegistered()) {
                        sp.registerYourself();
                    }
                    return sp;
                }
            });
        }
    }

    public static BotProgram createHostileProgram(GameData gameData) {
        return new BotProgram("Hostile",
                new MoveTowardsClosestActorMovement(gameData),
                new AttackAllActorsButNotTheseClasses(List.of(RobotCharacter.class)),
                new Sprite("hostilebot", "robots2.png", 13, 15, null));
    }
}

package model.programs;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.npcs.robots.RobotNPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;

import java.io.Serializable;

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

    public BotProgram(String name, MovementBehavior mov, ActionBehavior act) {
        this(name, mov, act, null);
    }

    public String getName() {
        return this.name;
    }

    public void loadInto(RobotNPC selectedBot, Actor whosAsking) {
        selectedBot.setMoveBehavior(this.moveBehave);
        selectedBot.setActionBehavior(this.actBehave);
        if (sprite != null) {
            selectedBot.setCharacter(new CharacterDecorator(selectedBot.getCharacter(), "sprite change") {

                @Override
                public Sprite getSprite(Actor whosAsking) {
                    return BotProgram.this.sprite;
                }
            });
        }
    }
}

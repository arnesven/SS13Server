package model.npcs.behaviors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.decorators.OneTurnAnimationDecorator;
import model.characters.general.RobotCharacter;
import model.events.animation.AnimatedSprite;
import util.MyRandom;

import java.util.List;

public class DoomsdayMachineBehavior implements ActionBehavior {
    private final AttackAllActorsButNotTheseClasses innerBehavior;

    public DoomsdayMachineBehavior() {
        innerBehavior = new AttackAllActorsButNotTheseClasses(List.of(RobotCharacter.class));
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        if (MyRandom.nextDouble() < 0.67) {
            innerBehavior.act(npc, gameData);
        } else {
            npc.setCharacter(new OneTurnAnimationDecorator(npc.getCharacter(), "doomsdayeffect", gameData) {
                @Override
                protected Sprite getAnimatedSprite() {
                    return new AnimatedSprite("doomsdayeffect", "effects3.png",
                            0, 4, 32, 32, npc, 16, true);
                }
            });
        }
    }
}

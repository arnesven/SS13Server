package model.npcs.robots;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;

public class DockWorkerBotCharacter extends RobotCharacter {
    public DockWorkerBotCharacter(int number, int i) {
        super("DockWorker #" + number, i, 20.0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("dockworkerbot", "aibots.png", 81, getActor());
    }
}

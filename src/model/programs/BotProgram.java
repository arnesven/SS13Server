package model.programs;

import model.npcs.RobotNPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;

import java.io.Serializable;

/**
 * Created by erini02 on 14/04/16.
 */
public class BotProgram implements Serializable {
    private MovementBehavior moveBehave;
    private ActionBehavior actBehave;
    private String name;

    public BotProgram(String name, MovementBehavior mov, ActionBehavior act) {
        this.name = name;
        this.moveBehave = mov;
        this.actBehave = act;
    }

    public String getName() {
        return this.name;
    }

    public void loadInto(RobotNPC selectedBot) {
        selectedBot.setMoveBehavior(this.moveBehave);
        selectedBot.setActionBehavior(this.actBehave);
    }
}

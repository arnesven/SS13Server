package model.characters.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;

import java.util.List;

/**
 * Created by erini02 on 20/10/16.
 */
public class MitosisAction extends Action {
    /**

     */
    public MitosisAction() {
        super("Mitosis", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "split into two!";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ActionBehavior ab = new DoNothingBehavior();
        MovementBehavior mov = new MeanderingMovement(0.5);
        GameCharacter chara = performingClient.getCharacter().clone();
        if (performingClient instanceof NPC) {
            ab = ((NPC) performingClient).getActionBehavior();
            mov = ((NPC) performingClient).getMovementBehavior();
        }

        gameData.addNPC(new NPC(chara, mov, ab, performingClient.getPosition()) {
            @Override
            public boolean hasInventory() {
                return true;
            }
        });
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}

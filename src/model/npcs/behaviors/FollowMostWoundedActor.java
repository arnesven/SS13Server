package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.events.FollowMovementEvent;
import model.map.Room;
import model.npcs.NPC;

/**
 * Created by erini02 on 18/04/16.
 */
public class FollowMostWoundedActor implements MovementBehavior {
    private final GameData gameData;
    private Actor toFollow;

    public FollowMostWoundedActor(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void move(NPC npc) {
        if (toFollow == null || whoNeedsMeMost(npc) != toFollow) {
            toFollow = whoNeedsMeMost(npc);
        }
        if (toFollow != npc) {
            gameData.addMovementEvent(new FollowMovementEvent(npc.getPosition(), npc, toFollow.getAsTarget(), true));

        }
    }

    private Actor whoNeedsMeMost(NPC npc) {
        Actor follow = npc.getPosition().getActors().get(0);
        for (Actor a : npc.getPosition().getActors()) {
            if (a != npc && isUnhealthier(a, follow) && a.getCharacter().isCrew()) {
                follow = a;
            }
        }
        return follow;
    }

    private boolean isUnhealthier(Actor a, Actor follow) {
        return  (a.getMaxHealth() - a.getCharacter().getHealth() <
                follow.getMaxHealth() - follow.getCharacter().getHealth());
    }
}

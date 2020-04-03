package model.characters.decorators;

import model.Actor;
import model.Target;
import model.characters.general.GameCharacter;
import model.events.FollowMovementEvent;

public class FollowingDecorator extends CharacterDecorator {
    private final FollowMovementEvent followEvent;
    private final Actor beingFollow;

    public FollowingDecorator(GameCharacter character, Target target, FollowMovementEvent followEvent) {
        super(character, "Following " + target.getName());
        this.followEvent = followEvent;
        this.beingFollow = (Actor)target;
    }

    public FollowMovementEvent getFollowEvent() {
        return followEvent;
    }

    public Actor getWhosBeingFallowed() {
        return beingFollow;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return super.getPublicName(whosAsking) + " (following " + beingFollow.getPublicName() + ")";
    }


}

package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.ActionOption;
import model.actions.general.WatchAction;
import model.events.FollowMovementEvent;
import model.items.general.GameItem;
import model.map.rooms.Room;
import util.MyRandom;

public class InvestigateAction extends WatchAction {

	private Room shadowedInRoom;

	public InvestigateAction(Actor actor) {
		super(actor);
		setName("Investigate");
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Shadowed";
	}

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (ActionOption opt2 : opt.getSuboptions()) {
            for (Actor a : whosAsking.getPosition().getActors()) {
                if (opt2.getName().equals(a.getPublicName())) {
                    if (a.getItems().size() > 0) {
                        opt2.addOption("Carrying " + MyRandom.sample(a.getItems()).getPublicName(whosAsking));
                        break;
                    }
                }
            }
        }
        return opt;
    }

//    @Override
//	protected void applyTargetingAction(GameData gameData,
//			final Actor performingClient, final Target target,
//			GameItem item) {
//		super.applyTargetingAction(gameData, performingClient, target, item);
//		performingClient.addTolastTurnInfo("You are shadowing " + target.getName());
//		this.shadowedInRoom = performingClient.getPosition();
//
//		gameData.addMovementEvent(new FollowMovementEvent(shadowedInRoom, performingClient, target, true));
//	}
	
}

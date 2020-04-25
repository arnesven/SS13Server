package model.actions.objectactions;

public class UnlockRoomAction { //extends Action {
//
//	private Room to;
//	private Room from;
//	private KeyCardLock lock;
//
//	public UnlockRoomAction(Room to, Room from, KeyCardLock keyCardLock) {
//		super("Unlock " + to.getName(), SensoryLevel.OPERATE_DEVICE);
//		this.to = to;
//		this.from = from;
//		this.lock = keyCardLock;
//	}
//
//	@Override
//	protected String getVerb(Actor whosAsking) {
//		return "unlocked the " + to.getName();
//	}
//
//	@Override
//	protected void execute(GameData gameData, Actor performingClient) {
//		if (GameItem.hasAnItem(performingClient, new UniversalKeyCard()) ||
//                performingClient.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof AICharacter)) {
//			lock.unlockRooms();
//			performingClient.addTolastTurnInfo("You unlocked the " + to.getName() + ".");
//
//		} else {
//			performingClient.addTolastTurnInfo("What? The key card is gone! Your action failed.");
//		}
//	}
//
//	@Override
//	public void setArguments(List<String> args, Actor p) {}

}

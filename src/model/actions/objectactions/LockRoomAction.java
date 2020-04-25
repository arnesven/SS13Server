package model.actions.objectactions;

public class LockRoomAction { //extends Action {
//
//	private Room to;
//	private Room from;
//	private KeyCardLock lock;
//
//	public LockRoomAction(Room to, Room from, KeyCardLock keyCardLock) {
//		super("Lock " + to.getName(), SensoryLevel.OPERATE_DEVICE);
//		this.to  = to;
//		this.from = from;
//		this.lock = keyCardLock;
//	}
//
//	@Override
//	protected String getVerb(Actor whosAsking) {
//		return "locked the " + to.getName();
//	}
//
//	@Override
//	protected void execute(GameData gameData, Actor performingClient) {
//		if (UniversalKeyCard.hasAnItem(performingClient, new UniversalKeyCard()) ||
//                performingClient.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof AICharacter)) {
//			lock.shutFireDoor();
//			performingClient.addTolastTurnInfo("You locked the " + to.getName() + ".");
//		} else {
//			performingClient.addTolastTurnInfo("What? The Key Card is gone! Your action failed.");
//		}
//	}
//
//	@Override
//	public void setArguments(List<String> args, Actor p) { }

}

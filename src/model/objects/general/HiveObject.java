package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.events.damage.Damager;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

public class HiveObject extends BreakableObject {

	private boolean foundByHumanTeam = false;
    private Actor finder = null;
    private List<String> damageHistory;

    public HiveObject(String name, Room pos) {
		super(name, 3.0, pos);
        damageHistory = new ArrayList<>();
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("hivebroken", "alien.png", 25);
        }
        return new Sprite("hive", "alien.png", 24);
    }

    @Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		
		if (isFound()){
			super.addYourselfToRoomInfo(info, whosAsking);
		} else if (whosAsking.isInfected()) {
            info.add(getSprite(whosAsking).getName() + "<img>" + this.getPublicName(whosAsking) + " (sensed)");

		} 
	}

    @Override
    public void beExposedTo(Actor performingClient, Damager damage) {
        super.beExposedTo(performingClient, damage);
        if (performingClient == null) {
            damageHistory.add(damage.getDamage() + " damage from " + damage.getName());
        } else {
            damageHistory.add(performingClient.getBaseName() + " did " + damage.getDamage() + " damage with " + damage.getName());
        }
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item) {
        boolean succ = super.beAttackedBy(performingClient, item);
        damageHistory.add(performingClient.getBaseName() + " did " + item.getDamage() + " damage with " + item.getBaseName());
        return succ;
    }

    public void setFound(boolean b) {
		foundByHumanTeam = b;
	}
	
	public boolean isFound() {
		return foundByHumanTeam;
	}

	@Override
	public boolean isTargetable() {
		return isFound();
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {	}


    public void setFinder(Actor finder) {
        this.finder = finder;
    }

    public Actor getFinder() {
        return finder;
    }

    public List<String> getDamageHistory() {
        return damageHistory;
    }
}

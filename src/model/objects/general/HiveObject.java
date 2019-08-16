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

public class HiveObject extends HideableObject {


    private List<String> damageHistory;

    public HiveObject(String name, Room pos) {
		super(name, 3.0, pos);
        damageHistory = new ArrayList<>();
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("hivebroken", "alien.png", 25, this);
        }
        return new Sprite("hive", "alien.png", 24, this);
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
        double healthBefore = getHealth();
        super.beExposedTo(performingClient, damage);
        if (healthBefore > getHealth()) {
            if (performingClient == null) {
                damageHistory.add(damage.getDamage() + " damage from " + damage.getName());
            } else {
                damageHistory.add(performingClient.getBaseName() + " did " + damage.getDamage() + " damage with " + damage.getName());
            }
        }
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item) {
        boolean succ = super.beAttackedBy(performingClient, item);
        damageHistory.add(performingClient.getBaseName() + " did " + item.getDamage() + " damage with " + item.getBaseName());
        return succ;
    }



	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {	}


    public List<String> getDamageHistory() {
        return damageHistory;
    }
}

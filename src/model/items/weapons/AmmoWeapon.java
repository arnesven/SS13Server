package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Player;


public class AmmoWeapon extends Weapon {

	protected int shots;
	private int maxshots;

	public AmmoWeapon(String string, double hitChance, double damage,
			boolean bang, double weight, int shots, int cost) {
		super(string, hitChance, damage, bang, weight, cost);
		this.shots = shots;
		this.maxshots = shots;
        this.setCriticalChance(0.10);
	}
	
	
	@Override
	public String getFullName(Actor whosAsking) {
		return super.getFullName(whosAsking) + "(" + shots + ")";
	}
	
	@Override
	public boolean isAttackSuccessful(boolean reduced) {
		shots--;
		return super.isAttackSuccessful(reduced);
	}

	@Override
	public boolean isAttackSuccessfulOnImmobileTarget() {
		shots--;
		return super.isAttackSuccessfulOnImmobileTarget();
	}

	@Override
	public boolean isReadyToUse() {
		return shots > 0;
	}

    @Override
    public AmmoWeapon clone() {
        return new AmmoWeapon(this.getBaseName(), super.getHitChance(),
                this.getDamage(), this.makesBang(), this.getWeight(), maxshots, this.getCost());
    }

    public int getMaxShots() {
        return maxshots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getShots() {
        return shots;
    }

	@Override
	public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
		if (performingClient.getItems().contains(this)) {
			return super.getExtraDescriptionStats(gameData, performingClient) +
					" <b>Ammo:</b> " + shots + "/" + maxshots + "<br/>";
		}
		return getExtraDescriptionStats(gameData, performingClient);
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Good for shooting people, or stuff.";
	}
}

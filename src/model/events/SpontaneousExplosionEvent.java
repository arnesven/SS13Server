package model.events;

import model.events.ambient.AmbientEvent;
import model.events.animation.BigExplosionAnimation;
import model.events.damage.ExplosiveDamage;
import sounds.Sound;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.map.rooms.Room;

public class SpontaneousExplosionEvent extends AmbientEvent {

    private static final double occurrenceChance = 0.02;

    public double getStaticProbability() {
		return occurrenceChance;
	}

    @Override
	public void apply(GameData gameData) {
		if (MyRandom.nextDouble() >= getProbability()) {
			return;
		}
		Room room = gameData.getNonHiddenStationRooms().get(MyRandom.nextInt(gameData.getNonHiddenStationRooms().size()));
		this.explode(room, gameData);

	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "spontaneous explosion";
	}
	
	public void explode(Room room, GameData gameData) {
		for (Target t : room.getTargets(gameData)) {
			t.beExposedTo(null, new ExplosiveDamage(1.0), gameData);
		}
		room.addToEventsHappened(this);
		room.addEvent(new BigExplosionAnimation(gameData, room));
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
							    AudioLevel.VERY_LOUD, OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public String getDistantDescription() {
		return "You hear a loud explosion";
	}

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return Sound.EXPLOSION;
    }

}

package model.items;

import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;

public interface Explosive {

	SensoryLevel SENSED_AS = new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
											  AudioLevel.VERY_LOUD, 
											  OlfactoryLevel.UNSMELLABLE);

}

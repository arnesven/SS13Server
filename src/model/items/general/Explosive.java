package model.items.general;

import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;

public interface Explosive {

	SensoryLevel SENSED_AS = new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
											  AudioLevel.VERY_LOUD, 
											  OlfactoryLevel.UNSMELLABLE);

}

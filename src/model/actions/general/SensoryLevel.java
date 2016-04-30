package model.actions.general;

import java.io.Serializable;

public class SensoryLevel implements Serializable {
	
	public enum VisualLevel {
		CLEARLY_VISIBLE,
		VISIBLE_IF_CLOSE,
		STEALTHY,
		INVISIBLE
	}
	
	public enum AudioLevel {
		VERY_LOUD,
		SAME_ROOM,
		HEAR_IF_CLOSE,
		ALMOST_QUIET,
		INAUDIBLE
	}
	
	public enum OlfactoryLevel {
		SHARP,
		WHIFF, 
		UNSMELLABLE
	}

	public static final SensoryLevel NO_SENSE = 
				new SensoryLevel(VisualLevel.INVISIBLE, AudioLevel.INAUDIBLE, OlfactoryLevel.UNSMELLABLE);

	public static final SensoryLevel SPEECH = new SensoryLevel(VisualLevel.VISIBLE_IF_CLOSE, 
										                       AudioLevel.SAME_ROOM,
										                       OlfactoryLevel.UNSMELLABLE);

	public static final SensoryLevel PHYSICAL_ACTIVITY =  new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
																		   AudioLevel.SAME_ROOM, 
																		   OlfactoryLevel.UNSMELLABLE);
	
	public static final SensoryLevel OPERATE_DEVICE = new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
			  														   AudioLevel.INAUDIBLE, 
			  														   OlfactoryLevel.UNSMELLABLE);

	public static final SensoryLevel FIRE =  new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
			  												  AudioLevel.SAME_ROOM, 
			  												  OlfactoryLevel.SHARP);
	
	public VisualLevel visual;
	public AudioLevel sound;
	public OlfactoryLevel smell;


	
	public SensoryLevel(VisualLevel v, AudioLevel a, OlfactoryLevel o) {
		visual = v;
		sound = a;
		smell = o;
	}

}

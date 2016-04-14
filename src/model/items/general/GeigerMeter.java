package model.items.general;

public class GeigerMeter extends GameItem {

	
	public GeigerMeter() {
		super("Geiger Meter", 0.2);
	}

	@Override
	public GeigerMeter clone() {
		return new GeigerMeter();
	}
	
	@Override
	protected char getIcon() {
		return 'd';
	}

}

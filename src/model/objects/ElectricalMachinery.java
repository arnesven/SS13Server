package model.objects;


public abstract class ElectricalMachinery extends BreakableObject implements Repairable {

	private boolean inUse = false;

	public ElectricalMachinery(String name) {
		super(name, 1.5);
	}

	public void setInUse(boolean b) {
		System.out.println("in use = " + b);
		this.inUse = b;
	}
	
	public boolean isInUse() {
		return inUse;
	}
	
}

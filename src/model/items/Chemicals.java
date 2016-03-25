package model.items;

public class Chemicals extends GameItem {

	public Chemicals() {
		super("Chemicals", 1.0);
	}

	@Override
	public Chemicals clone() {
		return new Chemicals();
	}

}

package model.items;

public class NuclearDisc extends GameItem {

	public NuclearDisc() {
		super("Nuclear Disc", 0.1);
	}

	@Override
	public NuclearDisc clone() {
		return new NuclearDisc();
	}

}

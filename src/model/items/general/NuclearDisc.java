package model.items.general;

public class NuclearDisc extends GameItem {

	public NuclearDisc() {
		super("Nuclear Disc", 0.1);
	}

	@Override
	public NuclearDisc clone() {
		return new NuclearDisc();
	}
	
	@Override
	protected char getIcon() {
		return 'n';
	}

}

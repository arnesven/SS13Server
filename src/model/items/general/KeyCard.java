package model.items.general;

public class KeyCard extends GameItem {



	public KeyCard() {
		super("Keycard", 0.1);
	}

	@Override
	public KeyCard clone() {
		return new KeyCard();
	}

	@Override
	protected char getIcon() {
		return 'y';
	}
}

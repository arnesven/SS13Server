package model;

public class GameItem {

	private String name;
	
	public GameItem(String string) {
		name = string;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}

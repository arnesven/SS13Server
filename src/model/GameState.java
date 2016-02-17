package model;

public enum GameState {
	
	PRE_GAME(0, "Pre-Game"),
	MOVEMENT(1, "Movement"),
	ACTIONS (2, "Actions");
	
	
	GameState(int val, String name) {
		this.val = val;
		this.name = name;
	}
	
	public final int val;
	public final String name;
}

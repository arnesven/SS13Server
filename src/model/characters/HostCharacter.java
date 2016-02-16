package model.characters;


public class HostCharacter extends InfectedCharacter {

	public HostCharacter(GameCharacter chara) {
		super(chara);
	}

	@Override
	public String getRealName() {
		return getName() + " (host)";
	}
}

package model.characters;


/**
 * @author erini02
 * Decorator for indicating that the inner character is the host.
 */
public class HostCharacter extends InfectedCharacter {

	public HostCharacter(GameCharacter chara) {
		super(chara, null);
	}

	@Override
	public String getFullName() {
		return getInner().getFullName() + " (host)";
	}
}

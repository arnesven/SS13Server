package model.characters.decorators;

import model.characters.general.GameCharacter;


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


	public static String getAntagonistDescription() {
		return "<font size=\"3\"><i>A parasitic hive has infested the station, spawning nasties and spreading a maddening disease. " +
				"You are the host and can spread it to others. The crew will want to burn out the infestation and " +
				"destroy the hive once they've found it - you must protect it!</i></font>";
	}

	@Override
	public String getMugshotName() {
		return "Host";
	}
}

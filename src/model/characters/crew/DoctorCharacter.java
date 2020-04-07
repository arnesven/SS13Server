package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.Target;
import model.characters.general.GameCharacter;
import model.items.Scalpel;
import model.items.general.Defibrilator;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.weapons.Knife;

public class DoctorCharacter extends CrewCharacter {

	public DoctorCharacter() {
		super("Doctor", SCIENCE_TYPE, 24, 12.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Scalpel());
        list.add(new Defibrilator());
		return list;
	}

	@Override
	public String getHowPerceived(Actor actor) {
		String usually = super.getHowPerceived(actor);
		Target t = actor.getAsTarget();
        if (!t.isDead()) {
            if (t.getHealth() < t.getMaxHealth()) {
                usually += " (unhealthy)";
            }
        }
		
		return usually;
	}

	@Override
	public GameCharacter clone() {
		return new DoctorCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 50;
    }

	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "Injuries are common for those working on the station. Patch people up and send them back to work again.",
				"Pro Injector, MedKit Conservation").makeString();
	}
}

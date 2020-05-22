package model.characters.crew;

import model.items.HandCuffs;
import model.items.general.GameItem;
import model.items.weapons.StunBaton;

import java.util.ArrayList;

public class SecurityGuardCharacter extends SecurityCharacter {
    public SecurityGuardCharacter() {
        super("Security Guard", 6.5);
    }

    @Override
    protected void addSpecificSecurityStartingGear(ArrayList<GameItem> list) {
        list.add(new StunBaton());
        list.add(new HandCuffs());
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "Any obtrusive, lewd or criminal activity is handled by you. Keep the crewmembers safe from each other!", "Suit up!").makeString();
    }
}

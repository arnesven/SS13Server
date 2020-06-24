package model.modes;

import model.npcs.CommandablePirateNPC;

import java.io.Serializable;

public class PirateBackStory implements Serializable {
    private final String skills;
    private String description;
    private CommandablePirateNPC pirate;

    public PirateBackStory(String descr, String skills) {
        this.description = descr;
        this.skills = skills;
    }

    public void setPirate(CommandablePirateNPC commandablePirateNPC) {
        this.pirate = commandablePirateNPC;
    }

    public String getDescription() {
        return description;
    }

    public String getSkills() {
        return skills;
    }
}

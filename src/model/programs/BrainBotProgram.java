package model.programs;

import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.items.Brain;
import model.items.NoSuchThingException;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BotConsole;

/**
 * Created by erini02 on 12/10/16.
 */
public class BrainBotProgram extends BotProgram {
    private final BotConsole console;
    private final GameData gameData;
    private final Brain brain;

    public BrainBotProgram(String programName, BotConsole console, GameData gameData, Brain brain) {
        super(programName, null, null);
        this.console = console;
        this.gameData = gameData;
        this.brain = brain;
    }

    @Override
    public void loadInto(RobotNPC selectedBot) {
        console.removeProgram(this, gameData);

        // switch the bots character with the brains character


        // move NPC to corpse location
        Room oldPosition = selectedBot.getPosition();
        try {
            selectedBot.getPosition().removeNPC(selectedBot);
        } catch (NoSuchThingException nste) {
            nste.printStackTrace(); // should not happen!
        }
        brain.getBelongsTo().getPosition().addNPC(selectedBot);

        // move brain Actor to NPCs old position
        try {
            if (brain.getBelongsTo() instanceof Player) {
                brain.getBelongsTo().getPosition().removePlayer(((Player)(brain.getBelongsTo())));
                oldPosition.addPlayer((Player)(brain.getBelongsTo()));
            } else {
                brain.getBelongsTo().getPosition().removeNPC(((NPC)(brain.getBelongsTo())));
                oldPosition.addNPC((NPC)(brain.getBelongsTo()));
            }
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }


        GameCharacter botCharacter = selectedBot.getCharacter();
        selectedBot.setCharacter(brain.getBelongsTo().getCharacter());
        brain.getBelongsTo().setCharacter(botCharacter);
    }
}

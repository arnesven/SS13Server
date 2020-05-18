package comm.chat;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.modes.GameMode;
import util.Logger;

import javax.imageio.ImageIO;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MugshotChatHandler extends ChatCommandHandler {
    private static Set<Sprite> mugshots;
    private static final String PATH_TO_MUGSHOTS = "resources/sprites/";

    public MugshotChatHandler() {
        super("mugshot");
    }

    public static void loadMugshots() {
        mugshots = new HashSet<>();
        List<String> jobnames = new ArrayList<>();
        jobnames.addAll(GameMode.getAvailCharsAsStrings());
        jobnames.add("Artificial Intelligence");
        jobnames.addAll(GameMode.availableAntagonists());
        int i = 0;
        for (String charname : jobnames) {
            try {
                if (fileExists(charname)) {
                    Sprite sp = new Sprite(charname + "mugshot", charname + "mugshot.png", 0, null);
                    mugshots.add(sp);
                    Logger.log("Loaded mugshot " + sp.getName());
                    i++;
                } else {
                    Logger.log("No mugshot for " + charname);
                }
            } catch (IllegalArgumentException iae) {
                Logger.log(Logger.INTERESTING, "No mugshot for " + charname);
            }
        }
        Logger.log(Logger.INTERESTING, "Mugshots found: " + i);
    }

    private static boolean fileExists(String charname) {
        if (MugshotChatHandler.class.getResourceAsStream("/sprites/" + charname + "mugshot.png") != null) {
            return true;
        }

        File file = new File("resources/sprites/" + charname + "mugshot.png");
        return file.exists();
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        if (sender.getCharacter() != null) {
            takeMugshot(sender.getCharacter().getMugshotName(), sender.getCharacter().getMugshotSprite(gameData, sender));
            gameData.getChat().serverSay("Flash! You took a mugshot of the " + sender.getCharacter().getMugshotName() + ". It will be saved for later reference.", sender);
        } else {
            gameData.getChat().serverSay("Can't take mugshot - you don't have a character yet!", sender);
        }
    }

    private void takeMugshot(String name, Sprite s) {
        File dir = new File(PATH_TO_MUGSHOTS);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedImage img = null;
        try {
            File outputFile = new File("./" + PATH_TO_MUGSHOTS +  name + "mugshot.png");
            ImageIO.write(s.getImage(), "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

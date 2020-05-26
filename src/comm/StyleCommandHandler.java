package comm;

import graphics.sprites.PhysicalBody;
import model.CharacterCreation;
import model.GameData;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class StyleCommandHandler extends AbstractCommandHandler {
    public StyleCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.equals("STYLE")) {
            if (rest.contains("LOAD")) {
                oos.writeObject(gameData.getPlayerForClid(clid).getStylePreviewName() + "<style-delim>" +
                        PhysicalBody.getContentAsStrings() + "<style-delim>" + gameData.getPlayerForClid(clid).getCharacterCreation().getHTML());
            } else if (rest.contains("RANDOM")) {
                // TODO also send which style elements are selected so the client can select them...
                gameData.getPlayerForClid(clid).setStyleBody(new PhysicalBody());
                oos.writeObject(gameData.getPlayerForClid(clid).getStylePreviewName());
            } else if (rest.contains("SET")) {
                if (rest.contains("HAIR")) {
                    String num = rest.replace("SET HAIR", "");
                    int hairNum = new Scanner(num).nextInt();
                    gameData.getPlayerForClid(clid).getStyleBody().setHairNumber(hairNum);
                } else if (rest.contains("FACE")) {
                    int facialNum = new Scanner(rest.replace("SET FACE", "")).nextInt();
                    gameData.getPlayerForClid(clid).getStyleBody().setFacialHairNumber(facialNum);
                } else if (rest.contains("HCOLOR")) {
                    String stripped = rest.replace("SET HCOLOR", "");
                    Scanner scanner = new Scanner(stripped);
                    gameData.getPlayerForClid(clid).getStyleBody().setHairColor(new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                } else if (rest.contains("FCOLOR")) {
                    String stripped = rest.replace("SET FCOLOR", "");
                    Scanner scanner = new Scanner(stripped);
                    gameData.getPlayerForClid(clid).getStyleBody().setFacialHairColor(new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                } else if (rest.contains("GENDER")) {
                    String stripped = rest.replace("SET GENDER", "");
                    gameData.getPlayerForClid(clid).getStyleBody().setGender(!stripped.contains("WOMAN"));
                }
                oos.writeObject(gameData.getPlayerForClid(clid).getStylePreviewName());
            } else if (rest.contains("EVENT")) {
                gameData.getPlayerForClid(clid).getCharacterCreation().update(rest.replace(" EVENT ", ""), gameData);
                oos.writeObject(gameData.getPlayerForClid(clid).getStylePreviewName() + "<style-delim>" +
                        PhysicalBody.getContentAsStrings() + "<style-delim>" + gameData.getPlayerForClid(clid).getCharacterCreation().getHTML());
            }
            return true;
        }
        return false;
    }
}

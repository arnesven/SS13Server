package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class SpriteManager {

    private static String[] path = new String[]{"actor.png", "item.png", "object.png",
		"fire.png",  "event.png", "radioactive.png",
		"cold.png", "unknown.png", "parasite.png", "parasite_dead.png",
		"alien.png", "snake.png", "chemicals.png",
		"nuclear_disc.png", "locker.png", "med_storage.png",
		"fuel_storage.png", "computer.png", "bioscanner.png",
		"applepie.png", "food.png", "weapon.png", "laserpistol.png",
		"shotgun.png", "fireext.png", "banana.png", "cookomatic.png",
		"knife.png", "chimp.png", "aicore.png", "pressurepanel.png",
		"device.png", "tars.png", "nuke.png", "medkit.png",
		"crate.png", "bible.png", "flamer.png", "stunbaton.png",
		"keycard.png", "cat.png", "hive.png", "husk.png", "naked_man.png",
		"naked_woman.png", "suit.png", "grenade.png", "gloves.png",
		"saxophone.png", "firesuit.png", "radsuit.png", "spacesuit.png",
		"opspacesuit.png", "chefshat.png", "sunglasses.png", "blue.png",
		"bottle.png", "candybar.png", "can.png", "chips.png", "cigarettes.png",
		"contour.png", "doughnut.png", "green.png", "lizardman.png",
		"pink.png", "syringe.png"};

	private static String[] keys = new String[]{"a", "i", "o", "f", "e", "r", "c", "?", "p", "P", "A", "(",
		"C", "n", "l", "m", "u", "@", "B", "[", "F", "w", "L", "g",
		"x", "b", "O", "k", "]", "I", ")", "d", "T", "N", "M", "#",
		"+", "{", "!", "y", "&", "H", "h", "%", "8", "U", "}", "2",
		"X", "q", "Q", "S", "0", "z", "Z", "_", "7", "Y", "$", "<",
		">", "*", "1", ",", "-", "J", "G"};

	private static ArrayList<String> keyList = new ArrayList<>();
	private static ArrayList<String> valList = new ArrayList<>();

	public static ImageIcon getSprite(final String spriteKey) {

		String val = lookUp(spriteKey);

		final ImageIcon ic = getBackUpSprite(spriteKey);
		if (val == null) {
			ServerCommunicator.send(GameData.getInstance().getClid() + " RESOURCE " + spriteKey, new MyCallback<String>() {

				@Override
				public void onSuccess(String result) {
					if (!result.contains("ERROR")) {
						addToDic(spriteKey, result);
						ic.setImage(setBase64(result));

					}
				}
			});
		} else {
            BufferedImage image = setBase64(val);
			ic.setImage(image);
		}

		return ic;
	}

	protected static BufferedImage setBase64(String result) {
        byte[] data = Base64.getDecoder().decode(result);
        ByteArrayInputStream bai = new ByteArrayInputStream(data);
        BufferedImage img = null;
        try {
            img = ImageIO.read(bai);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;

        //	image.setUrl("data:image/png;base64,"+result);

	}

	protected static void addToDic(String spriteKey, String result) {
		keyList.add(spriteKey);
		valList.add(result);
	}

	private static String lookUp(String string) {
		int i = 0;

		for (String key : keyList) {
			if (string.equals(key)) {
				return valList.get(i);
			}
			i++;
		}
		return null;
	}

    private static ImageIcon getBackUpSprite(String string) {
        ImageIcon ic = null;
        boolean found = false;
        try {
            for (int i = 0; i < keys.length; ++i) {
                if (string.equals(keys[i])) {
                    ic = new ImageIcon(ImageIO.read(SpriteManager.class.getResourceAsStream("clientresources/images/" + path[i])));
                    //image.setUrl(GWT.getModuleBaseURL()+"images/" + path[i]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                 ic = new UnknownIcon();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ic;
    }

}

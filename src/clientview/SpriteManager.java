package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientview.components.MapPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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

	private static Map<String, ImageIcon> map = new HashMap<>();
	private static Map<String, Image> originalMap = new HashMap<>();

	public static ImageIcon getSprite(final String spriteKey) {

		ImageIcon val = lookUp(spriteKey);

		final ImageIcon ic = getBackUpSprite(spriteKey);
		if (val == null) {
			ServerCommunicator.send(GameData.getInstance().getClid() + " RESOURCE " + spriteKey, new MyCallback<String>() {

				@Override
				public void onSuccess(String result) {
					if (!result.contains("ERROR")) {
						ic.setImage(setBase64(result));
						addToDic(spriteKey, ic);
					}
				}

				@Override
				public void onFail() {
					System.out.println("Failed to send RESOURCE message to server");
				}
			});
		} else {
			return val;
		}

		return ic;
	}

	public static ImageIcon getSprite(final String spriteKey, int zoom) {
		ImageIcon ic = getSprite(spriteKey);
		if (ic.getIconWidth() != zoom) {
			if (zoom == 64) {
				rescaleImage(ic, zoom / 32.0);
			} else {
				ic.setImage(originalMap.get(spriteKey));
			}
		}
		return ic;
	}

	public static BufferedImage setBase64(String result) {
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

	protected static void addToDic(String spriteKey, ImageIcon image) {
		//keyList.add(spriteKey);
		//valList.add(result);
		originalMap.put(spriteKey, image.getImage());
		if (MapPanel.getZoom() != 32) {
			rescaleImage(image, MapPanel.getZoom() / 32);
		}
		map.put(spriteKey, image);
	}

	private static ImageIcon lookUp(String string) {
		return map.get(string);
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

	public static void drawSprite(String spriteName, Graphics g, int x, int y) {
		ImageIcon ic = SpriteManager.getSprite(spriteName);
		g.drawImage(ic.getImage(), x, y, null);
	}

	public static void rescaleImages(int i) {
		for (String key : map.keySet()) {
			ImageIcon ic = map.get(key);
			if (i == 64) {
				rescaleImage(ic, 2.0);
			} else {
				ic.setImage(originalMap.get(key));
			}
		}
	}

	private static void rescaleImage(ImageIcon ic, double scale) {
		BufferedImage buf = new BufferedImage((int)(ic.getIconWidth()*scale),
				(int)(ic.getIconHeight()*scale), BufferedImage.TYPE_INT_ARGB);
		Graphics g = buf.getGraphics();
		g.drawImage(ic.getImage(), 0, 0, buf.getWidth(), buf.getHeight(),
				0, 0, ic.getIconWidth(), ic.getIconHeight(), null);
		ic.setImage(buf);
	}
}

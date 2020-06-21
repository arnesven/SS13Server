package util;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteManager;

import java.awt.image.BufferedImage;

/**
 * Created by erini02 on 13/11/16.
 */
public class HTMLText {
    public static String wikiURL = "https://gitlab.ida.liu.se/erini02/ss13/wikis";

    public static String makeText(String color, String s) {
        return "<font color=\"" + color + "\">" + s + "</font>";
    }

    public static String makeText(String color, String font, int size, String s) {
        return "<font color=\"" + color + "\" face=\"" + font + "\" size=\"" + size + "\">" + s + "</font>";
    }


    public static String makeText(String fgColor, String bgColor, String s) {
        return "<font color=\"" + fgColor + "\" style=\"background-color:" + bgColor + "\">" + s + "</font>";
    }


    public static String makeText(String fgcolor, String bgcolor, String font, int size, String s) {
        return "<font color=\"" + fgcolor + "\" face=\"" + font + "\" size=\"" +
                size + "\" style=\"background-color:" + bgcolor + "\">" + s + "</font>";
    }

    public static String makeLink(String addr, String title) {
        return "<a style=\"text-decoration:underline;\" target=\"_info\" href=\""+addr+"\">"+title+"</a>";
    }

    public static String makeWikiLink(String addr, String title) {
        return "<a style=\"text-decoration:underline;\" target=\"_info\" href=\""+wikiURL+"/"+addr+"\">"+title+"</a>";
    }

    public static String makeColoredBackground(String color, String s) {
        return "<body style=\"background-color:" + color + "\">" + s + "</body>";
    }

    public static String makeCentered(String s) {
        return "<center>" + s + "</center>";
    }

    public static String makeImage(Sprite sprite) {
        String decodeded = SpriteManager.encode64(sprite);
        return "<img src=\"data:image/png;base64,"+ decodeded + "\"></img>";
    }

    public static String makeImage(BufferedImage img) {
        String decoded = SpriteManager.encode64(img);
        return "<img src=\"data:image/png;base64,"+ decoded + "\"></img>";
    }

    public static String makeFancyFrameLink(String command, String text) {
        return "<a href=\"https://EVENT." + command + "\"" + ">" + text + "</a>";
    }

    public static String makeGrayButton(boolean isPushed, String command, String buttonText) {
        return makeFancyFrameLink(command, makeText(isPushed?"white":"black", isPushed?"black":"gray", "Courier", 5, buttonText));
    }

    public static String makeBox(String fg, String bg, int widthPct, boolean border, String content) {
        return "<table width=\"" + widthPct + "%\" border=\"" + (border?"1":"0") +
                "\" fgcolor=\"" + fg  + "\" bgcolor=\"" + bg+ "\"rules=\"rows\"><tr><td>" + content + "</td></tr></table>";
    }

    public static String makeBox(String fg, String bg, String content) {
        return "<table width=\"100%\" border=\"1\" fgcolor=\"" + fg  + "\" bgcolor=\"" + bg+ "\"rules=\"rows\"><tr><td>" + content + "</td></tr></table>";
    }
}

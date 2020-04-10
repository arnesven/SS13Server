package util;

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
        return "<body style=\"background-color:" + color + ";\">" + s + "</body>";
    }

    public static String makeCentered(String s) {
        return "<center>" + s + "</center>";
    }
}

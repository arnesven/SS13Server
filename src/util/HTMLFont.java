package util;

/**
 * Created by erini02 on 13/11/16.
 */
public class HTMLFont {
    public static String makeText(String color, String s) {
        return "<font color=\"" + color + "\">" + s + "</font>";
    }

    public static Object makeText(String color, String font, int size, String s) {
        return "<font color=\"" + color + "\" face=\"" + font + "\" size=\"" + size + "\">" + s + "</font>";
    }
}

package util;

import java.io.PrintStream;

/**
 * Created by erini02 on 19/04/16.
 */
public class Logger {

    public static final int OFF = 0;
    public static final int CRITICAL = 1;
    public static final int INTERESTING = 2;
    public static final int VERBOSE = 3;

    private static int currentLevel = VERBOSE;
    private static PrintStream out = System.out;

    public static void log(int level, String mess, boolean newline) {
        if (level <= currentLevel) {
            if (newline) {
                out.println(mess);
            } else {
                out.print(mess);
            }
        }
    }


    public static void log(int level, String mess) {
        log(level, mess, true);
    }

    public static void log(String mess, boolean newline) {
        log(VERBOSE, mess, newline);
    }

    public static void log(String mess) {
        log(VERBOSE, mess, true);
    }


}

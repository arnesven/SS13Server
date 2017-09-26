package util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by erini02 on 26/09/17.
 */
public class MyArrays {

    public static Collection<Integer> parseIntArray(String string) {
        String noBrace = string.substring(1, string.length()-1);
        ArrayList<Integer> result = new ArrayList<>();
        if (noBrace.contains(",")) {
            String[] ints = noBrace.split(", ");
            for (int i = 0; i < ints.length; ++i) {
                result.add(Integer.parseInt(ints[i]));
            }
        } else {
            result.add(Integer.parseInt(noBrace));
        }
        return result;
    }
}

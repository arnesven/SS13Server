package util;

import model.Actor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 16/09/17.
 *
 * A class full of juicy util methods for handeling maps.
 */
public class MyMaps {
    public static <F, E> Map<F,E> reverseMap(Map<E, F> lookup) {
        Map<F, E> reversedMap = new HashMap<>();
        for (Map.Entry<E, F> entry : lookup.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }
}

package sounds;

import shared.SoundManager;
import util.Logger;

import java.util.HashMap;
import java.util.Map;

public class ServerSoundManager extends SoundManager {

    private static Map<String, String> register = new HashMap<>();

    public static void register(Sound sound) {
        if (!register.keySet().contains(sound.getSource())) {
            register.put(sound.getSource(), getSoundAsBase64(sound.getSource()));
        }
    }

    public static String getBase64String(String rest) {
        if (register.containsKey(rest)) {
            return register.get(rest);
        }
        Logger.log(Logger.CRITICAL, "Could not find sound resource for key '" + rest + "'");
        return "";
    }
}

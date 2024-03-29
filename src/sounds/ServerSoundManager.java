package sounds;

import shared.SoundManager;
import util.Logger;

import java.util.HashMap;
import java.util.Map;

public class ServerSoundManager extends SoundManager {

    private static Map<String, String> base64Register = new HashMap<>();
    private static Map<String, Sound> soundRegister = new HashMap<>();

    public static void register(Sound sound) {
        if (!base64Register.keySet().contains(sound.getName())) {
            base64Register.put(sound.getName(), getSoundAsBase64(sound.getSource()));
            soundRegister.put(sound.getName(), sound);
        }
    }

    private static String getBase64String(String rest) {
        if (base64Register.containsKey(rest)) {
            return base64Register.get(rest);
        }
        Logger.log(Logger.CRITICAL, "Could not find sound resource for key '" + rest + "'");
        return "";
    }

    public static String getSoundData(String rest) {
        if (soundRegister.containsKey(rest)) {
            return getBase64String(rest) + "<sprt>" + soundRegister.get(rest).getVolume();
        }
        Logger.log(Logger.CRITICAL, "Could not find sound resource for key '" + rest + "'");
        return "<sprt>0.0";
    }
}

package shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class SoundManager {
    public static String getSoundAsBase64(String rest) {
        InputStream is = SoundManager.class.getResourceAsStream("/sound/zombie.mp3");
        try {
            byte[] byteArr = is.readAllBytes();
            String result = Base64.getEncoder().encodeToString(byteArr);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decodeAsBase64(String result) {
        return Base64.getDecoder().decode(result);
    }
}

package sounds;

import java.io.Serializable;

/**
 * Created by erini02 on 15/12/16.
 */
public class Sound implements Serializable {

    private final String source;

    public Sound(String source) {
        this.source = source;

        ServerSoundManager.register(this);
    }

    public String getSource() {
        return source;
    }
}

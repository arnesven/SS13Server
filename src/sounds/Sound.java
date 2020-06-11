package sounds;

import java.io.Serializable;

/**
 * Created by erini02 on 15/12/16.
 */
public class Sound implements Serializable {

    private final String source;
    private float volume;

    public Sound(String source) {
        this.source = source;
        this.volume = 0.0f;
        ServerSoundManager.register(this);
    }

    public String getSource() {
        return source;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
}

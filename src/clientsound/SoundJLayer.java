package clientsound;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SoundJLayer extends PlaybackListener implements Runnable
{
    private final InputStream inputStream;
    private AdvancedPlayer player;
    private Thread playerThread;
    private boolean doesRepeat = false;
    private boolean isPlaying;

    public SoundJLayer(String filePath, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
    }

    public SoundJLayer(byte[] byteData, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = new ByteArrayInputStream(byteData);
    }


    public SoundJLayer(String filePath) {
        this(filePath, false);
    }

    public SoundJLayer(byte[] byteData) {
        this(byteData, false);
    }


    public void play()
    {
        try
        {

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stop() {
        if (isPlaying) {
            this.player.stop();
            try {
                doesRepeat = false;
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackStarted()");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackEnded()");
    }

    // Runnable members

    public void run()
    {
        isPlaying = true;
        do {
            try {
                this.player = new AdvancedPlayer
                        (
                                inputStream,
                                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
                        );

                this.player.setPlayBackListener(this);
                this.player.play();
            } catch (javazoom.jl.decoder.JavaLayerException ex) {
                ex.printStackTrace();
            }
        } while (doesRepeat);
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}

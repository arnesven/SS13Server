package clientsound;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import shared.SoundManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSoundManager extends SoundManager {

    private static Map<String, ClientSound> loadedSounds = new HashMap<>();
    private static SoundJLayer effectsSoundQueue;
    private static String backgroundSound = "nothing";
    private static SoundJLayer bgSoundLayer;

    public static void playSound(String key, String clid) {
        SoundJLayer sjl = new SoundJLayer(getSoundResource(key, clid));
        sjl.play();
    }

    private static ClientSound getSoundResource(String key, String clid) {
        if (!loadedSounds.keySet().contains(key)) {
            loadFromServer(key, clid);
        }
        return loadedSounds.get(key);
    }

    private static void loadFromServer(String key, String clid) {
        ServerCommunicator.send(clid + " RESOURCE SOUND " + key, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                String parts[] = result.split("<sprt>");
                byte[] bytes = SoundManager.decodeAsBase64(parts[0]);
                loadedSounds.put(key, new ClientSound(bytes, Float.parseFloat(parts[1])));
            }

            @Override
            public void onFail() {
                System.out.println("Failed to send RESOURCE SOUND message to server");
            }
        });
    }



    public static void playSoundsInSuccession(String[] split, String clid) {
        List<ClientSound> byteList = new ArrayList<>();
        for (String s : split) {
            System.out.println("Sound to play: " + s);
            byteList.add(getSoundResource(s, clid));
        }
        if (effectsSoundQueue == null || !effectsSoundQueue.isPlaying()) {
            effectsSoundQueue = new SoundJLayer(byteList);
            effectsSoundQueue.play();
        } else {
            effectsSoundQueue.addToQueue(byteList);
        }

    }

    public static synchronized void playBackgroundSound(String ambientSound, String clid) {
        if (!backgroundSound.equals(ambientSound)) {
            if (bgSoundLayer != null) {
                bgSoundLayer.stop();
                System.out.println("STOPPING AMBIENT SOUND");
            }
            if (!ambientSound.equals("nothing")) {
                bgSoundLayer = new SoundJLayer(getSoundResource(ambientSound, clid), true);
                bgSoundLayer.play();
                System.out.println("STARTING AMBIENT SOUND");
            }
            backgroundSound = ambientSound;
        }
    }

    public static void stopPlayingBackgroundSound() {
        if (bgSoundLayer != null) {
            bgSoundLayer.stop();
            System.out.println("STOPPING AMBIENT SOUND");
        }
    }
}

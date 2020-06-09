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

    private static Map<String, byte[]> loadedSounds = new HashMap<>();
    private static SoundJLayer effectsSoundQueue;

    public static void playSound(String key, String clid) {
        SoundJLayer sjl = new SoundJLayer(getSoundResource(key, clid));
        sjl.play();
    }

    private static byte[] getSoundResource(String key, String clid) {
        if (!loadedSounds.keySet().contains(key)) {
            loadFromServer(key, clid);
        }
        return loadedSounds.get(key);
    }

    private static void loadFromServer(String key, String clid) {
        ServerCommunicator.send(clid + " RESOURCE SOUND " + key, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                byte[] bytes = SoundManager.decodeAsBase64(result);
                loadedSounds.put(key, bytes);
            }

            @Override
            public void onFail() {
                System.out.println("Failed to send RESOURCE SOUND message to server");
            }
        });
    }


    private static void writeToFile(String key) {

        if (!(new File("./loadedsounds").exists())) {
            new File("./loadedsounds").mkdir();
        }
        File f = new File("./loadedsounds/" + key + ".mp3");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bos.write(loadedSounds.get(key));
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void playSoundsInSuccession(String[] split, String clid) {
        List<byte[]> byteList = new ArrayList<>();
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
}

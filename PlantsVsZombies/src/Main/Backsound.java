package Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Backsound {

    private Clip dayClip;

    public Backsound(String dayFilePath) {
        try {
            // Memuat file audio untuk siang hari
            File dayFile = new File(dayFilePath);
            AudioInputStream dayStream = AudioSystem.getAudioInputStream(dayFile);
            dayClip = AudioSystem.getClip();
            dayClip.open(dayStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playDaySound() {
        if (!dayClip.isRunning()) {
            dayClip.setFramePosition(0);
            dayClip.loop(Clip.LOOP_CONTINUOUSLY);
            dayClip.start();
        }
    }

    public void stop() {
        if (dayClip.isRunning()) {
            dayClip.stop();
        }
    }
}

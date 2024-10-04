package sonstiges;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Die Klasse ermöglicht das Abspielen von Sounds und Hintergrundmusik
 */
public class SoundManager {
	
	private static Clip clip;
	

    /**
     * Spielt ein SoundEffekt ab
     * @param filePath
     */
    public static void playSound(String filePath) {
        try {
            // Lade die Audiodatei über getResource
            URL soundFile = SoundManager.class.getResource(filePath);
            if (soundFile == null) {
                System.out.println("Datei nicht gefunden: " + filePath);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Startet die Wiedergabe
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spielt Hintergrund-Musik ab als Loop
     * @param filePath
     */
    public static void playLoop(String filePath) {
        try {
            URL soundFile = SoundManager.class.getResource(filePath);
            if (soundFile == null) {
                System.out.println("Datei nicht gefunden: " + filePath);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Endlosschleife
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stoppt die Hintergrundmusik
     */
    public static void stopLoop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();   // Stoppt den Sound
            clip.close();  // Schließt den Clip und gibt Ressourcen frei
            clip.flush();
        }
        if (clip == null)
        {
        	System.out.println("test");
        }
    }
}

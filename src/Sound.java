import java.net.URL;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class provides methods and fields to easily create, play and stop sounds
 * in the game.
 * @see stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
 */
public final class Sound
{
  private final String path;
  private Clip clip;
  private AudioInputStream audioIn;

  /**
   * Create a new sound, defined by the file at the given path.
   * @param path the relative file path to the sound to create.
   */
  public Sound(String path)
  {
    this.path = path;
    //http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    try
    {
      // Open an audio input stream.
      URL url = this.getClass().getClassLoader().getResource(path);
      this.audioIn = AudioSystem.getAudioInputStream(url);
      // Get a sound clip resource.
      this.clip = AudioSystem.getClip();
      // Open audio clip and load samples from the audio input stream.
      this.clip.open(this.audioIn);
    }
    catch (UnsupportedAudioFileException e)
    {
      System.err.println("The sound " + this.path + " is invalid.");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (LineUnavailableException e)
    {
      e.printStackTrace();
    }
    catch (IllegalArgumentException e)
    {
    }
  }

  /**
   * Play the sound. Restart it from the beginning if it is already started.
   */
  public void play()
  {
    try
    {
      this.clip.setFramePosition(0);
      this.clip.start();
    }
    catch (Exception e)
    {
      System.err.println("Impossible to read the sound " + this.path + ".");
    }
  }

  /**
   * Stop the sound.
   */
  public void stop()
  {
    try
    {
      this.clip.stop();
      this.clip.close();
    }
    catch (Exception e)
    {
      System.err.println("Impossible to stop the sound " + this.path + ".");
    }
  }

  /**
   * State whether or not the song is playing.
   * @return true if the song is currently playing, false otherwise.
   */
  public boolean isPlaying()
  {
    boolean bool = false;
    try
    {
      bool = clip.isRunning();
    }
    catch (Exception e)
    {
      System.err.println("Impossible to check the sound " + this.path + ".");
    }
    return bool;
  }
}

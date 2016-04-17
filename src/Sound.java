import java.net.URL;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.embed.swing.JFXPanel;
import java.lang.Thread;
import java.lang.Runnable;

public class Sound
{
  private final String path;
  private final Media media;
  private final MediaPlayer mediaPlayer;
  
  static
  {
    JFXPanel fxPanel = new JFXPanel();
  }
  
  public Sound(String path)
  {
    this.path = path;
    URL file = this.getClass().getClassLoader().getResource(this.path);
    this.media = new Media(file.toString());
    this.mediaPlayer = new MediaPlayer(this.media);
  }

  public void play()
  {
    this.mediaPlayer.play();
  }

  public void stop()
  {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() ->
    {
      this.mediaPlayer.stop();
    });
  }

  public boolean isPlaying()
  {
    return false;
  }
}
/*
public class Sound
{
  private final String path;
  private Clip clip;
  private AudioInputStream audioIn;

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

  public void play()
  {
    try
    {
      this.clip.start();
    }
    catch (Exception e)
    {
      System.err.println("Impossible to read the sound " + this.path + ".");
    }
  }

  public void stop()
  {
    try
    {
      this.clip.stop();
    }
    catch (Exception e)
    {
      System.err.println("Impossible to stop the sound " + this.path + ".");
    }
  }

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
*/

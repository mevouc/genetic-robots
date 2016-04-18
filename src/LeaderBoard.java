import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/**
 * This class defines the properties of the leader board and how it sort the
 * scores.
 * @see Score
 */
public final class LeaderBoard
{
  private File scoresBackUp;
  private List<Score> scores;

  // create a file to save the scores if no file exists
  private void createFileIfNecessary(File file)
  {
    try
    {
      if (!file.exists())
      {
        try
        {
          file.createNewFile();
        }
        catch (Exception e)
        {
          System.err.println("Can't create scores file.");
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("Can't check scores file existence.");
    }
  }

  /**
   * Create a new leader board associated with the given path.
   * @param path the path of the file
   */
  public LeaderBoard(String path)
  {
    this.scoresBackUp = new File(path);
    createFileIfNecessary(this.scoresBackUp);
    this.scores = new ArrayList<Score>();
  }

  private boolean setWritableIfNecessary(File file)
  {
    try
    {
      if (!file.canWrite())
      {
        try
        {
          file.setWritable(true);
        }
        catch (Exception e)
        {
          System.err.println("Can't set scores file writable.");
          return false;
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("Can't check if scores file is writable.");
      return false;
    }
    return true;
  }

  // create the object to write in the file
  private FileWriter createWriter(File file)
  {
    FileWriter fw = null;
    try
    {
      fw = new FileWriter(file);
    }
    catch (Exception e)
    {
      System.err.println("Can't write in scores file.");
      fw = null;
    }
    return fw;
  }

  // save the score in the file
  private boolean saveScore(FileWriter writer, Score score)
  {
    try
    {
      writer.write(score.toString());
      writer.write("\n");
      writer.flush();
    }
    catch (Exception e)
    {
      System.err.println("can't save scores.");
      return false;
    }
    return true;
  }

  // save all the scores in the file
  private void saveScores()
  {
    if (!setWritableIfNecessary(this.scoresBackUp))
      return;
    FileWriter writer = createWriter(this.scoresBackUp);
    if (writer == null)
      return;
    for (Score score : scores)
    {
      if (!saveScore(writer, score))
        return;
    }
  }

  private boolean setReadableIfNecessary(File file)
  {
    try
    {
      if (!file.canRead())
      {
        try
        {
          file.setReadable(true);
        }
        catch (Exception e)
        {
          System.err.println("Can't set scores file readable.");
          return false;
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("Can't check if scores file is readable.");
      return false;
    }
    return true;
  }

  // create object to read in file
  private FileReader createReader(File file)
  {
    FileReader fd = null;
    try
    {
      fd = new FileReader(file);
    }
    catch (Exception e)
    {
      System.err.println("Can't read in scores file.");
      fd = null;
    }
    return fd;
  }

  // read all the chars in the file
  private String readChars(FileReader reader)
  {
    int len = 512;
    char[] cbuf = new char[len];
    try
    {
      reader.read(cbuf, 0, len);
    }
    catch (Exception e)
    {
      System.err.println("Can't read in scores files.");
      return null;
    }
    return new String(cbuf);
  }

  // transform the string read in the file into an ArrayList of Scores
  private ArrayList<Score> processOnString(String str)
  {
    String[] lines = str.split("\n");
    ArrayList<Score> scores = new ArrayList<Score>(lines.length - 1);
    for (int i = 0; i < lines.length - 1; i++)
      scores.add(i, new Score(lines[i].split(" ")));
    return scores;
  }

  // read the scores in  the file and save them in the instance field scores
  private void readScores()
  {
    if (!setReadableIfNecessary(this.scoresBackUp))
      return;
    FileReader reader = createReader(this.scoresBackUp);
    if (reader == null)
      return;
    String str = readChars(reader);
    if (str == null)
      return;
    this.scores = processOnString(str);
  }

  // sort the scores
  private void sortScores()
  {
    Collections.sort(this.scores);
  }

  // keep the 5 best scores
  private void keep5Scores()
  {
    while (this.scores.size() > 5)
      this.scores.remove(5);
  }

  /**
   * Get a string representation of the leaderboard.
   * @return a string representing on 6 lines the 5 best scores and columns
   * titles
   */
  public String toString()
  {
    String str = "Pseudo     Robots  Time\n";
    for (int i = 0; i < this.scores.size(); i++)
      str += (i + 1) + " " + this.scores.get(i).format() + "\n";
    return str;
  }

  /**
   * Add a new Score in the leader board and get a string representing the new
   * leader board.
   * @param newScore the score to add in the leader board
   * @return a string representing on 6 lines the 5 best scores and columns
   * titles
   */
  public String addScore(Score newScore)
  {
    readScores();
    this.scores.add(newScore);
    sortScores();
    keep5Scores();
    saveScores();
    return this.toString();
  }
}

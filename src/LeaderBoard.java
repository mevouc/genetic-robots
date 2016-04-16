import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class LeaderBoard
{
  private File scoresBackUp;
  private List<Score> scores;

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

  private ArrayList<Score> processOnString(String str)
  {
    String[] lines = str.split("\n");
    ArrayList<Score> scores = new ArrayList<Score>(lines.length - 1);
    for (int i = 0; i < lines.length - 1; i++)
      scores.add(i, new Score(lines[i].split(" ")));
    return scores;
  }

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

  private void sortScores()
  {
    Collections.sort(this.scores);
  }

  private void keep5Scores()
  {
    while (this.scores.size() > 5)
      this.scores.remove(5);
  }

  public String toString()
  {
    String str = "Pseudo     Robots  Time\n";
    for (int i = 0; i < this.scores.size(); i++)
      str += (i + 1) + " " + this.scores.get(i).format() + "\n";
    return str;
  }

  // return the String representation of the leaderboard 
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

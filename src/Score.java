public class Score implements Comparable<Score>
{
  private String pseudo;
  private long robotsKilled;
  private long timePlayed;

  public Score(String pseudo)
  {
    this.pseudo = pseudo;
    this.robotsKilled = 0;
    this.timePlayed = 0;
  }

  public Score(String[] tokens)
  {
    this("");
    if (tokens.length < 2)
      System.err.println("Invalid format of String.");
    this.robotsKilled = Integer.parseInt(tokens[0]);
    this.timePlayed = Integer.parseInt(tokens[1]);
    if (tokens.length >= 3)
    {
      this.pseudo = tokens[2];
      for (int i = 3; i < tokens.length; i++)
        this.pseudo += " " + tokens[i];
    }
  }

  public long getRobotsKilled()
  {
    return this.robotsKilled;
  }

  public void setTimePlayed(long timePlayed)
  {
    this.timePlayed = timePlayed;
  }

  public void increment()
  {
    this.robotsKilled++;
  }

  public String toString()
  {
    return String.format("%d %d %s", robotsKilled, timePlayed, pseudo);
  }

  public String format()
  {
    char[] pseudo = new char[14];
    for (int i = 0; i < 14; i++)
    {
      if (i < this.pseudo.length())
        pseudo[i] = this.pseudo.charAt(i);
      else
        pseudo[i] = ' ';
    }
    return String.format("%s %2d %s", new String(pseudo), this.robotsKilled,
        new Chrono(this.timePlayed).minAndSec());
  }

  public int compareTo(Score that)
  {
    if (that == null)
      throw new NullPointerException();
    if (this.robotsKilled != that.robotsKilled)
      return (int)(that.robotsKilled - this.robotsKilled);
    return (int)(that.timePlayed - this.timePlayed);
  }
}

public class Score
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

  public void setTimePlayed(long timePlayed)
  {
    this.timePlayed = timePlayed;
  }
  
  public String toString()
  {
    return String.format("%d %d %s", robotsKilled, timePlayed, pseudo);
  }
}

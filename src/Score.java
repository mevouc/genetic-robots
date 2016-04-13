public class Score
{
  private String pseudo;
  private long robotsKilled;
  private long waveReached;
  private long timePlayed;

  public Score(String pseudo)
  {
    this.pseudo = pseudo;
    this.robotsKilled = 0;
    this.waveReached = 1;
    this.timePlayed = 0;
  }

  public void setTimePlayed(long timePlayed)
  {
    this.timePlayed = timePlayed;
  }
}

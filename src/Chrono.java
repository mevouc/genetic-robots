public class Chrono
{
  private long time;

  public Chrono()
  {
    this.time = 0;
  }

  public long getTime()
  {
    return this.time;
  }

  public void setTime(long time)
  {
    this.time = time;
  }

  public String toString()
  {
    long second = (time / 1000) % 60;
    long minute = (time / (1000 * 60)) % 60;
    long hour = (time / (1000 * 60 * 60)) % 24;
    long milli = (time % 1000) / 10;
    if (hour != 0)
      return String.format("%02d:%02d:%02d:%02d", hour, minute, second, milli);
    if (minute != 0)
      return String.format("%02d:%02d:%02d", minute, second, milli);
    return String.format("%02d:%02d", second, milli);
  }
}

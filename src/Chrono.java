/**
 * This class defines the behaviour of the timer of the game. It contains few
 * methods interacting with a single long, representing a time in milliSeconds.
 */
public final class Chrono
{
  private long time;

  /**
   * Create a new timer, initialized to 0.
   */
  public Chrono()
  {
    this.time = 0;
  }

  /**
   * Create a new timer with a given time already counted.
   * @param time the initial time of the chrono.
   */
  public Chrono(long time)
  {
    this.time = time;
  }

  /**
   * Get the time represented by this chrono.
   */
  public long getTime()
  {
    return this.time;
  }

  /**
   * Set the time of this chrono to the given time.
   * @param time the time of the chrono.
   */
  public void setTime(long time)
  {
    this.time = time;
  }

  /**
   * Generate a string to represent this chrono, in minutes and seconds.
   * @return a string of 5 char representing this chrono.
   */
  public String minAndSec()
  {
    long second = (time / 1000) % 60;
    long minute = (time / (1000 * 60));
    return String.format("%02d:%02d", minute, second);
  }

  /**
   * Generate a string representing this chrono, in hours, minutes, seconds,
   * and/or milliseconds, depending on the amount of time represented.
   * @return a string of 5, 8, or 11 char representing this chrono.
   */
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

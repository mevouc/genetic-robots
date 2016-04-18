import java.awt.Color;

/**
 * This class represents the animation of blood produced by a shot hit.
 * @see Animation
 */
public final class Blood extends Animation
{
  /**
   * Create blood of a certain color at a given position, with a given angle.
   * @param position the position of the blood.
   * @param angle the direction of the splash (in degrees).
   * @param color the blood color.
   */
  public Blood(Vector position, double angle, Color color)
  {
    this.position = position;
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = false;
    this.appearence = new BloodParticles(angle, color);
  }
}

/**
 * This class defines the appearence of the Gun of the player.
 * @see Template
 * @see Player
 */
public final class Gun extends Template
{
  /**
   * Create the gun with the given relative position and angle.
   * @param relativePos the relative position
   * @param relativeAngle the relative angle
   */
  public Gun(Vector relativePos, double relativeAngle)
  {
    super(relativePos, relativeAngle);
  }

  // what to draw
  private void draw(double x, double y, double angle)
  {
    SteveDraw.picture(x, y, "img/gun.png", angle - 90);
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

import java.awt.Color;

/**
 * This class defines the appearence of a bullet thrown by a robot.
 * @see Template
 * @see Shot
 */
public final class RobotBullet extends Template
{
  private final Color color;
  
  public RobotBullet(Color color)
  {
    super();
    this.color = color;
  }

  // draw a rotated rectangle
  private void draw(double x, double y, double theta)
  {
    SteveDraw.filledRectangle(x, y, 0.016, 0.0042, theta);
  }
  
  /**
   * {@inheritDoc}
   */
  protected void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(this.color);
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

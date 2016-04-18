import java.awt.Color;

/**
 * This class defines the appearence of the eyes of the robot.
 * @see Template
 * @see Robot
 */
public final class RobotEye extends Template
{
  /**
   * Create an eye with the given relative position.
   */
  public RobotEye(Vector relativePos)
  {
    super(relativePos, 0);
  }

  /**
   * {@inheritDoc}
   */
  protected void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.filledCircle(position.cartesian(0), position.cartesian(1), 0.004);
  }
}

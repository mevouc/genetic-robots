/**
 * This class defines the appearence of the head of the robots.
 * @see Template
 * @see Robot
 */
public final class RobotHead extends Template
{
  /**
   * Create a new robot head.
   */
  public RobotHead()
  {
    super();
    childs.add(new RobotEye(new Vector(0.025, 0.015)));
    childs.add(new RobotEye(new Vector(0.025, - 0.015)));
  }

  // what to draw
  private void draw(double x, double y, double angle)
  {
    SteveDraw.picture(x, y, "img/robothead.png", angle - 90);
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

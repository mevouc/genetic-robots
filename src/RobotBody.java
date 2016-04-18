import java.awt.Color;

/**
 * This class defines the appearence of the robots bodies.
 * @see Template
 * @see Robot
 */
public final class RobotBody extends Template
{
  private final Color color;
  private final double size;

  /**
   * Create the body of the given color, size.
   * @param color the color of the body
   * @param size the size
   */
  public RobotBody(Color color, double size)
  {
    super();
    this.color = color;
    this.size = size;
    childs.add(new RobotHead());
  }

  // bound a value between 2 others
  private double bound(double a, double left, double right)
  {
    if (right < left)
    {
      double tmp = left;
      left = right;
      right = tmp;
    }
    if (a < left)
      return left;
    if(a > right)
      return right;
    return a;
  }

  // determine the size of the pointer to this robot, depending on the distance
  private double sizePointer(double distance)
  {
    double radius = size / 1.5 - (distance - 0.5) / 10;
    double min = 0.01;
    if (radius < min)
      radius = min;
    return radius;
  }

  // display on the edge of the screen a pointer to the robot
  private void pointer(Vector position)
  {
    double x = bound(position.cartesian(0), 0.02, 0.98);
    double y = bound(position.cartesian(1), 0.02, 0.98);
    Vector center = new Vector(0.5, 0.5);
    Vector direction = position.minus(center);
    double distance = direction.magnitude();
    SteveDraw.filledTriangle(x, y, sizePointer(distance), direction.angle());
  }

  // what to draw
  private void draw(double x, double y, double angle)
  {
    SteveDraw.filledCircle(x, y, size / 2);
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(this.color);
    Vector playerPos = GeneticRobots.getPlayer().getPosition();
    double x = position.cartesian(0);
    double y = position.cartesian(1);
    if (x < 0 || x > 1 || y < 0 || y > 1)
      pointer(position);
    else
      draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

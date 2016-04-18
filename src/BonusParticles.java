import java.awt.Color;

/**
 * This class defines the behaviour of the appearence of the group of particles
 * attached to a bonus.
 * @see Template
 * @see Bonus
 */
public final class BonusParticles extends Template
{
  private final Color color;
  private final double radius;

  /**
   * Create this group of particles with the given radius and the given color.
   * @param radius the radius of the group of particles.
   * @param color the color of the particles.
   */
  public BonusParticles(double radius, Color color)
  {
    super(new Vector(0, 0), 0);
    this.color = color;
    this.radius = radius;
    for (int i = 0; i < 8; i++)
    {
      Vector relativePos = new Vector(radius, 0);
      childs.add(new BonusParticle(relativePos.rotate(i * 45), color));
    }
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

  // determine the size of the pointer to this bonus, depending on the distance
  private double sizePointer(double distance)
  {
    double size = radius * 2 / 1.5 - (distance - 0.5) / 10;
    double min = 0.01;
    if (size < min)
      size = min;
    return size;
  }

  // display on the edge of the screen a pointer to the bonus
  private void pointer(Vector position)
  {
    double x = bound(position.cartesian(0), 0.02, 0.98);
    double y = bound(position.cartesian(1), 0.02, 0.98);
    Vector center = new Vector(0.5, 0.5);
    Vector direction = position.minus(center);
    double distance = direction.magnitude();
    SteveDraw.setPenColor(Color.white);
    SteveDraw.filledTriangle(x, y, sizePointer(distance), direction.angle());
    SteveDraw.setPenColor(this.color);
    SteveDraw.triangle(x, y, sizePointer(distance), direction.angle());
  }

  /**
   * {@inheritDoc}
   */
  protected void display(Vector position, double angle)
  {
    Vector playerPos = GeneticRobots.getPlayer().getPosition();
    double x = position.cartesian(0);
    double y = position.cartesian(1);
    if (x < 0 || x > 1 || y < 0 || y > 1)
      pointer(position);
  }
}

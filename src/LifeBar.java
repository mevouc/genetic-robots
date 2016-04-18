import java.awt.Color;

/**
 * This class defines the behaviour and the appearence of the life bar attached
 * to robots and player.
 * @see Player
 * @see Robot
 */
public final class LifeBar
{
  private final double halfWidth;
  private final double halfHeight;

  /**
   * Create a life bar with the given half width and half height.
   * @param halfWidth the half width
   * @param halfHeight the half height
   */
  public LifeBar(double halfWidth, double halfHeight)
  {
    this.halfWidth = halfWidth;
    this.halfHeight = halfHeight;
  }

  /**
   * Draw the life bar at the given position with the given factor of its usual
   * size.
   * @param position the position where draw the life bar.
   * @param factor the factor to rescale the life bar.
   */
  public void draw(Vector position, double factor)
  {
    SteveDraw.setPenColor(Color.white);
    double x = position.cartesian(0);
    double y = position.cartesian(1);
    SteveDraw.filledRectangle(x, y, this.halfWidth * factor, this.halfHeight);
    SteveDraw.rectangle(x, y, this.halfWidth, this.halfHeight);
  }
}

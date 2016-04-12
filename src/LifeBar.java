import java.awt.Color;

public class LifeBar
{
  private final double halfWidth;
  private final double halfHeight;

  public LifeBar(double halfWidth, double halfHeight)
  {
    this.halfWidth = halfWidth;
    this.halfHeight = halfHeight;
  }

  public void draw(Vector position, double factor)
  {
    SteveDraw.setPenColor(Color.white);
    double x = position.cartesian(0);
    double y = position.cartesian(1);
    SteveDraw.filledRectangle(x, y, this.halfWidth * factor, this.halfHeight);
    SteveDraw.rectangle(x, y, this.halfWidth, this.halfHeight);
  }
}

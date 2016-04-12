import java.awt.Color;

public class RobotBody extends Template
{
  private final Color color;
  private final double size;

  public RobotBody(Color color, double size, Vector relativePos, double angle)
  {
    super(relativePos, angle);
    this.color = color;
    this.size = size;
  }

  private void draw(double x, double y, double angle)
  {
    SteveDraw.filledCircle(x, y, size);
  }

  public void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(this.color);
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

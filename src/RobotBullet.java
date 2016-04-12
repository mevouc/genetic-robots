import java.awt.Color;

public class RobotBullet extends Template
{
  Color color;
  
  public RobotBullet(Vector relativePos, double relativeAngle, Color color)
  {
    super(relativePos, relativeAngle);
    this.color = color;
  }

  private void draw(double x, double y, double theta)
  {
    SteveDraw.filledRectangle(x, y, 0.016, 0.0042, theta);
  }
  
  protected void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(this.color);
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

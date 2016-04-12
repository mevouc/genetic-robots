public class RobotHead extends Template
{
  public RobotHead(Vector relativePos, double relativeAngle)
  {
    super(relativePos, relativeAngle);
  }

  private void draw(double x, double y, double angle)
  {
    SteveDraw.picture(x, y, "img/robothead.png", angle - 90);
  }

  public void display(Vector position, double angle)
  {
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

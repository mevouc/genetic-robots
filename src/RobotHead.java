public class RobotHead extends Template
{
  public RobotHead(Vector relativePos, double relativeAngle)
  {
    super(relativePos, relativeAngle);
    childs.add(new RobotEye(new Vector(0.025, 0.015), 0));
    childs.add(new RobotEye(new Vector(0.025, - 0.015), 0));
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

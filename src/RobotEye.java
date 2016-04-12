import java.awt.Color;

public class RobotEye extends Template
{
  public RobotEye(Vector relativePos, double relativeAngle)
  {
    super(relativePos, relativeAngle);
  }

  protected void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.filledCircle(position.cartesian(0), position.cartesian(1), 0.004);
  }
}

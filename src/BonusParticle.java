import java.awt.Color;

public class BonusParticle extends Template
{
  private final Color color;

  public BonusParticle(Vector relativePos, Color color)
  {
    super(relativePos, 0);
    this.color = color;
  }

  protected void display(Vector relativePos, double relativeAngle)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.filledCircle(relativePos.cartesian(0), relativePos.cartesian(1), 0.004);
    SteveDraw.setPenColor(this.color);
    SteveDraw.circle(relativePos.cartesian(0), relativePos.cartesian(1), 0.004);
  }
}

import java.awt.Color;

public class BonusParticles extends Template
{
  public BonusParticles(double radius, Color color)
  {
    super(new Vector(0, 0), 0);
    for (int i = 0; i < 8; i++)
    {
      Vector relativePos = new Vector(radius, 0);
      childs.add(new BonusParticle(relativePos.rotate(i * 45), color));
    }
  }

  protected void display(Vector relativePos, double relativeAngle)
  {
    return;
  }
}

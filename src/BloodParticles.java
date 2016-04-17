import java.awt.Color;

public class BloodParticles extends ScalableTemplate
{
  private final Color color;

  public BloodParticles(double relativeAngle, Color color)
  {
    super(new Vector(0, 0), relativeAngle, 0.1);
    this.color = color;
  }

  public void display(Vector position, double angle)
  {
    double varAngle = 90;
    int nbParticles = 10;
    SteveDraw.setPenColor(this.color);
    for (int i = 0; i < nbParticles; i++)
    {
      Vector vect = (new Vector(1, 0)).rotate(varAngle * (0.5 + Math.random()));
      vect = vect.times(0.02 + Math.random() * 0.01);
      Vector fromSrc = vect.times(this.scale).rotate(angle).plus(position);
      SteveDraw.filledCircle(fromSrc.cartesian(0), fromSrc.cartesian(1), 0.003);
    }
  }
}

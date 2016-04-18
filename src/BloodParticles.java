import java.awt.Color;

/**
 * This class represents the appearence of blood splash, and its evolution. 
 * @see Blood
 * @see ScalableTemplate
 */
public final class BloodParticles extends ScalableTemplate
{
  private final Color color;

  /**
   * Create the particles with the given angle of splash and the given color.
   * @param relativeAngle the angle of which the splash will occur.
   * @param color the color of the particles.
   */
  public BloodParticles(double relativeAngle, Color color)
  {
    super(new Vector(0, 0), relativeAngle, 0.1);
    this.color = color;
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    // angles of variation in which the particles will appear
    double varAngle = 90;
    // number of particles at each frame
    int nbParticles = 5;
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

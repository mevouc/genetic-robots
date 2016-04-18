import java.awt.Color;

/**
 * This class defines the bevaviour of a unique bonus particle.
 * @see Template
 * @see BonusParticles
 * @see Bonus
 */
public final class BonusParticle extends Template
{
  private final Color color;

  /**
   * Create a bonus particle with a relative position from the center of the
   * group and a given color.
   * @param relativePos the position of the particle relatively to the center of
   * the group.
   * @param color the color of this particle.
   */
  public BonusParticle(Vector relativePos, Color color)
  {
    super(relativePos, 0);
    this.color = color;
  }

  /**
   * {@inheritDoc}
   */
  protected void display(Vector relativePos, double relativeAngle)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.filledCircle(relativePos.cartesian(0), relativePos.cartesian(1),
        0.004);
    SteveDraw.setPenColor(this.color);
    SteveDraw.circle(relativePos.cartesian(0), relativePos.cartesian(1), 0.004);
  }
}

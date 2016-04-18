/**
 * This class represents the appearence of an Explosion, and its evolution.
 * @see Explosion
 * @see ScalableTemplate
 */
public final class Flames extends ScalableTemplate
{
  /**
   * Initialize the fields of this ScalableTemplate.
   */
  public Flames()
  {
    // the native scale is 0
    super(0);
  }

  /**
   * {@inheritedDoc}
   */
  protected void display(Vector position, double angle)
  {
    // depending on the scale, the sprite to display is different.
    int indexAnim = (int)(5 * (1 - this.scale));
    if (indexAnim >= 5)
      return;
    SteveDraw.picture(position.cartesian(0), position.cartesian(1),
        "img/explosion" + indexAnim + ".png");
  }
}

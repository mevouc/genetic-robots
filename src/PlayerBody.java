/**
 * This class defines the appearence of the body of the player.
 * @see Template
 * @see Player
 */
public final class PlayerBody extends Template
{
  /**
   * Create the body.
   */
  public PlayerBody()
  {
    super();
    this.childs.add(new Gun(new Vector(0.025, 0.0), 0));
  }

  // draw the sprite
  private void draw(double x, double y)
  {
    SteveDraw.picture(x, y, "img/playerbody.png", 0);
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    draw(position.cartesian(0), position.cartesian(1));
  }
}

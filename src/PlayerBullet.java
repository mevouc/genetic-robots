/**
 * This class defines the appearence of a bullet thrown by the player.
 * @see Template
 * @see Shot
 */
public final class PlayerBullet extends Template
{
  /**
   * Initialize with default values the fields of the template.
   */
  public PlayerBullet()
  {
    super();
  }

  /**
   * {@inheriteDoc}
   */
  protected void display(Vector position, double angle)
  {
    SteveDraw.picture(position.cartesian(0), position.cartesian(1),
        "img/playerbullet.png", angle - 90);
  }
}

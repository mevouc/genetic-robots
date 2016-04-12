public class PlayerBullet extends Template
{
  public PlayerBullet(Vector relativePos, double relativeAngle)
  {
    super(relativePos, relativeAngle);
  }

  protected void display(Vector position, double angle)
  {
    SteveDraw.picture(position.cartesian(0), position.cartesian(1), "img/playerbullet.png", angle - 90);
  }
}
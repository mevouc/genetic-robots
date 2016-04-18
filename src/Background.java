/**
 * This class defines the appearence of the ground.
 * @see Ground
 * @see Template
 */
public final class Background extends Template
{
  private final double spriteSizeX;
  private final double spriteSizeY;

  /**
   * Initialize the background fields.
   */
  public Background()
  {
    super();
    this.spriteSizeX = 512.0 / GeneticRobots.canvasW;
    this.spriteSizeY = 512.0 / GeneticRobots.canvasH;
  }

  // what to draw
  private void draw(double x, double y)
  {
    SteveDraw.picture(x, y, "img/bg.png");
  }

  // bound x between 0 and 1
  private double boundX(double x)
  {
    while (x < 0)
      x += spriteSizeX;
    while (x > 1)
      x -= spriteSizeX;
    return x;
  }

  // bound 1 between 0 and 1
  private double boundY(double y)
  {
    while (y < 0)
      y += spriteSizeY;
    while (y > 1)
      y -= spriteSizeY;
    return y;
  }

  /**
   * {@inheritDoc}
   */
  public void display(Vector position, double angle)
  {
    double tmpX = position.cartesian(0);
    double tmpY = position.cartesian(1);
    double x = boundX(tmpX);
    double y = boundY(tmpY);
    // only display 9 tiles of the background
    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
        draw(x + i * spriteSizeX, y + j * spriteSizeY);
    }
  }
}

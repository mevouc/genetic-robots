public class Background extends Template
{
  private final double spriteSizeX;
  private final double spriteSizeY;

  public Background()
  {
    super();
    spriteSizeX = 512.0 / GeneticRobots.canvasW;
    spriteSizeY = 512.0 / GeneticRobots.canvasH;
  }

  private void draw(double x, double y)
  {
    SteveDraw.picture(x, y, "img/bg.png");
  }

  private double boundX(double x)
  {
    while (x < 0)
      x += spriteSizeX;
    while (x > 1)
      x -= spriteSizeX;
    return x;
  }

  private double boundY(double y)
  {
    while (y < 0)
      y += spriteSizeY;
    while (y > 1)
      y -= spriteSizeY;
    return y;
  }

  public void display(Vector position, double angle)
  {
    double tmpX = position.cartesian(0);
    double tmpY = position.cartesian(1);
    double x = boundX(tmpX);
    double y = boundY(tmpY);
    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
        draw(x + i * spriteSizeX, y + j * spriteSizeY);
    }
  }
}

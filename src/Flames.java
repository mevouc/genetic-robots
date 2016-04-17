public class Flames extends ScalableTemplate
{
  public Flames()
  {
    this.scale = 1;
  }

  protected void display(Vector position, double angle)
  {
    int indexAnim = (int)(5 * (1 - this.scale));
    SteveDraw.picture(position.cartesian(0), position.cartesian(1), "img/explosion" + indexAnim + ".png");
  }
}

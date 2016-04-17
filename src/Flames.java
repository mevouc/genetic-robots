public class Flames extends ScalableTemplate
{
  public Flames()
  {
    super(0);
  }

  protected void display(Vector position, double angle)
  {
    int indexAnim = (int)(5 * (1 - this.scale));
    if (indexAnim >= 5)
      return;
    SteveDraw.picture(position.cartesian(0), position.cartesian(1),
        "img/explosion" + indexAnim + ".png");
  }
}

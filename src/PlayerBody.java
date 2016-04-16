import java.awt.Color;

public class PlayerBody extends Template
{
  private final Color color;
  private final double size;

  public PlayerBody(Color color, double size, Vector relativePos, double angle)
  {
    super(relativePos, angle);
    this.color = color;
    this.size = size;
    childs.add(new Gun(new Vector(0.025, 0.0), 0));
  }

  private void draw(double x, double y, double angle)
  {
    SteveDraw.picture(x, y, "img/playerbody.png", 0);
  }

  public void display(Vector position, double angle)
  {
    SteveDraw.setPenColor(this.color);
    draw(position.cartesian(0), position.cartesian(1), angle);
  }
}

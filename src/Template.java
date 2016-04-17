import java.util.Collection;
import java.util.LinkedList;

public abstract class Template
{
  protected final Vector relativePos;
  protected final double relativeAngle;
  protected final Collection<Template> childs;

  protected Template()
  {
    this.relativePos = new Vector(0, 0);
    this.relativeAngle = 0;
    childs = new LinkedList<Template>();
  }

  protected Template(Vector relativePos, double relativeAngle)
  {
    this.relativePos = relativePos;
    this.relativeAngle = relativeAngle;
    childs = new LinkedList<Template>();
  }

  // display template at an absolute position
  protected abstract void display(Vector position, double angle);

  public void render(Vector position, double angle)
  {
    double theta = angle + relativeAngle;
    Vector where = position.plus(relativePos.rotate(angle));
    this.display(where, theta);
    for (Template child : childs)
      child.render(where, theta);
  }
}

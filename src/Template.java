import java.util.Collection;
import java.util.LinkedList;

/**
 * This abstract class is a recursive one. It defines the behaviour of the
 * component displaying for every GameObject. Each instance knows where it must
 * be situated from its parent. It then displays itself at a given position, and
 * inform all its child of this position so that they can diplay themselves.
 */
public abstract class Template
{
  // the relative position from the parent
  protected final Vector relativePos;
  // the relative angle from the parent
  protected final double relativeAngle;
  // all the subcomponent of this instance
  protected final Collection<Template> childs;

  /**
   * Creates a template with default values of relative position and angle.
   */
  protected Template()
  {
    this.relativePos = new Vector(0, 0);
    this.relativeAngle = 0;
    this.childs = new LinkedList<Template>();
  }

  /**
   * Creates a template with the given relative position and angle, but no
   * childs.
   * @param relativePos the relative position of this template from its parent
   * @param relativeAngle the relative angle of this template compared to its
   * parent.
   */
  protected Template(Vector relativePos, double relativeAngle)
  {
    this.relativePos = relativePos;
    this.relativeAngle = relativeAngle;
    this.childs = new LinkedList<Template>();
  }

  /**
   * Display template at an absolute position with a given angle.
   * @param position the absolute position to display the template.
   * @param angle the angle of which the template must be rotated.
   */
  protected abstract void display(Vector position, double angle);

  /**
   * Render the template at an absolute position, considering its relative
   * position and angle. And then ask all the childs of rendering themselves at
   * this position.
   */
  public void render(Vector position, double angle)
  {
    double theta = angle + this.relativeAngle;
    Vector where = position.plus(this.relativePos.rotate(angle));
    this.display(where, theta);
    for (Template child : childs)
      child.render(where, theta);
  }
}

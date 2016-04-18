/**
 * This class defines the behaviour of all moving objects, except particles. It
 * implements methods for accelerating, braking, moving, and so on.
 * @see GameObject
 */
public abstract class MovingObject extends GameObject
{
  protected Vector speed;
  protected Vector direction;
  // the capacity this object has to accelerate
  protected final double speedUp;
  private final double slowDown;
  protected final double maxSpeed;
  protected long elapsedTime;
  protected boolean goingUp, goingDown, goingLeft, goingRight;

  /**
   * Create a new moving object with the given capacity to accelerate and the
   * given maximum speed.
   * @param speedUp the capacity to accelerate.
   * @param maxSpeed the maximum speed.
   */
  protected MovingObject(double speedUp, double maxSpeed)
  {
    this.position = new Vector(2);
    this.speed = new Vector(2);
    // the default direction of this object
    this.direction = new Vector(0.0, 1.0);
    this.speedUp = speedUp;
    // all objects are on the same floor, so they all brake almost the same way
    this.slowDown = 0.00021;
    this.maxSpeed = maxSpeed;
  }

  /**
   * Create a moving object with a default capacity to accelerate and maximum
   * speed.
   */
  protected MovingObject()
  {
    this(0.00042, 0.01);
  }

  /**
   * Make this object to slow down.
   */
  protected void brake()
  {
    if (this.speed.magnitude() < this.slowDown)
      this.speed = new Vector(0.0, 0.0);
    else
    {
      Vector reduction = this.speed.direction().times(- this.slowDown
          * this.elapsedTime);
      this.speed = this.speed.plus(reduction);
    }
  }

  /**
   * Apply the given acceleration vector to this object.
   * @param acceleration the acceleration vector.
   */
  protected void accelerate(Vector acceleration)
  {
    if (acceleration.isZero())
      return;
    acceleration = acceleration.direction().times(this.speedUp
        * this.elapsedTime);
    this.speed = this.speed.plus(acceleration);
    if (this.speed.magnitude() > this.maxSpeed)
      this.speed = this.speed.direction().times(this.maxSpeed);
  }

  /**
   * Accelerate depending on the direction this object is currently going to.
   */
  protected void accelerate()
  {
    double[] acceleration = { 0, 0 };
    acceleration[0] += this.goingRight ? 1 : 0;
    acceleration[0] -= this.goingLeft ? 1 : 0;
    acceleration[1] += this.goingUp ? 1 : 0;
    acceleration[1] -= this.goingDown ? 1 : 0;
    accelerate(new Vector(acceleration));
  }

  /**
   * Move this object according to his current speed.
   */
  protected void move()
  {
    this.position = position.plus(this.speed);
    if (!this.speed.isZero())
      this.direction = this.speed.direction();
  }

  /**
   * Move this object to the given direction.
   * @param direction the vector defining where the object must go.
   */
  protected void move(Vector direction)
  {
    Vector tmpSpeed = this.speed.rotate(- direction.angle());
    if (tmpSpeed.cartesian(0) < 0)
      tmpSpeed = new Vector(0.0, tmpSpeed.cartesian(1));
    this.speed = tmpSpeed.rotate(direction.angle());
    move();
  }
}

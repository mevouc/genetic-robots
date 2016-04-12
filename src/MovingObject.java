public abstract class MovingObject extends GameObject
{
  protected Vector speed;
  protected Vector direction;
  protected final double speedUp; // capacity to accelerate
  private final double slowDown;
  protected final double maxSpeed;
  protected long elapsedTime;
  protected boolean goingUp, goingDown, goingLeft, goingRight;

  protected MovingObject(double speedUp, double maxSpeed)
  {
    this.position = new Vector(2);
    this.speed = new Vector(2);
    this.direction = new Vector(0.0, 1.0); // default direction
    this.speedUp = speedUp;
    this.slowDown = 0.00021; // default value
    this.maxSpeed = maxSpeed;
  }

  protected MovingObject()
  {
    this(0.00042, 0.01);
  }

  protected void brake()
  {
    if (speed.magnitude() < slowDown)
      speed = new Vector(0.0, 0.0);
    else
      speed = speed.plus(speed.direction().times(- slowDown * elapsedTime));
  }

  protected void accelerate(Vector acceleration)
  {
    if (acceleration.isZero())
      return;
    acceleration = acceleration.direction().times(speedUp * elapsedTime);
    speed = speed.plus(acceleration);
    if (speed.magnitude() > maxSpeed)
      speed = speed.direction().times(maxSpeed);
  }

  protected void accelerate()
  {
    double[] acceleration = { 0, 0 };
    acceleration[0] += goingRight ? 1 : 0;
    acceleration[0] -= goingLeft ? 1 : 0;
    acceleration[1] += goingUp ? 1 : 0;
    acceleration[1] -= goingDown ? 1 : 0;
    accelerate(new Vector(acceleration));
  }

  protected void move()
  {
    position = position.plus(speed);
    if (!speed.isZero())
      direction = speed.direction();
  }

  protected void move(Vector direction)
  {
    Vector tmpSpeed = speed.rotate(- direction.angle());
    if (tmpSpeed.cartesian(0) < 0)
      tmpSpeed = new Vector(0.0, tmpSpeed.cartesian(1));
    speed = tmpSpeed.rotate(direction.angle());
    move();
  }
}

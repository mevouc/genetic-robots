import java.awt.Color;

public class Robot extends MovingObject
{
  private final double firePower;
  private long life;
  private static Color[] colors = { Color.black, Color.blue, Color.red, Color.white };
  private final Collider collider;

  public Robot(Vector pos, double speedUp, double maxSpeed, double firePower, long life)
  {
    super(speedUp, maxSpeed);
    this.position = pos;
    this.firePower = firePower;
    this.life = life;
    this.direction = new Vector(Math.random() - 0.5, Math.random() - 0.5);
    Color color = colors[(int)(Math.random() * 4)];
    color = Color.red;
    appearence = new RobotBody(color, 0.042, new Vector(2), 0);
    collider = new Collider(0.021, position, "robot", this);
    GeneticRobots.addCollider(collider);
  }

  private void loseLife(long lifeLost)
  {
    this.life -= lifeLost;
  }

  private void die()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  public void update(long elapsedTime)
  {
    if (life <= 0)
      die();
    else
    {
      Vector oldPosition = this.position;
      this.elapsedTime = elapsedTime;
      Vector aim = GeneticRobots.getPlayer().getPosition().minus(position);
      isRight = (aim.cartesian(0) > 0);
      isLeft = (aim.cartesian(0) < 0);
      isUp = (aim.cartesian(1) > 0);
      isDown = (aim.cartesian(1) < 0);
      if (!speed.isZero())
        brake();
      accelerate(aim);
      move();
      Collision collision;
      if ((collision = collider.isColliding()) != null)
      {
        if (collision.getTag() == "playerShot")
        {
          Shot shot = (Shot)(collision.getObject());
          loseLife(shot.getDamage());
          shot.destroy();
        }
        else
        {
          this.position = oldPosition;
          move(collision.getForce());
        }
      }
      collider.setPosition(this.position);
    }
  }

  public void render()
  {
    appearence.render(GeneticRobots.centerOnPlayer(position), direction.angle());
  }
}

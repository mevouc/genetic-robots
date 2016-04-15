import java.awt.Color;

public class Robot extends MovingObject
{
  private final double damage;
  private final double maxLife;
  private double life;
  private final LifeBar lifeBar;
  private final double fireFreq;
  private final Color color;
  private final Collider collider;
  private long lastShot;

  public Robot(Vector pos, double speedUp, double maxSpeed, double damage, double life, double fireFreq, Color color)
  {
    super(speedUp, maxSpeed);
    this.position = pos;
    this.damage = damage;
    this.maxLife = life;
    this.life = this.maxLife;
    this.fireFreq = fireFreq;
    this.lifeBar = new LifeBar(0.03, 0.002);
    this.direction = new Vector(Math.random() - 0.5, Math.random() - 0.5);
    this.color = color;
    appearence = new RobotBody(this.color, 0.032, new Vector(2), 0);
    collider = new Collider(0.025, position, Tag.ROBOT, this);
    GeneticRobots.addCollider(collider);
  }

  private void loseLife(double lifeLost)
  {
    this.life -= lifeLost;
    if (this.life < 0)
      this.life = 0;
  }

  private void die()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
    GeneticRobots.getPlayer().incrementScore();
  }

  public boolean isDead()
  {
    return this.life <= 0;
  }

  private void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - lastShot < this.fireFreq)
      return;
    Shot shot = new Shot(position, direction.times(0.021), damage, Tag.ROBOTSHOT, color);
    GeneticRobots.addObject(shot);
    lastShot = System.currentTimeMillis();
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
      goingRight = (aim.cartesian(0) > 0);
      goingLeft = (aim.cartesian(0) < 0);
      goingUp = (aim.cartesian(1) > 0);
      goingDown = (aim.cartesian(1) < 0);
      shoot(direction);
      if (!speed.isZero())
        brake();
      accelerate(aim);
      move();
      Collision collision;
      if ((collision = collider.isColliding()) != null)
      {
        Tag tag = collision.getTag();
        if (tag != Tag.ROBOTSHOT && tag != Tag.BONUS)
        {
          if (tag == Tag.PLAYERSHOT)
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
      }
      collider.setPosition(this.position);
    }
  }

  public void render()
  {
    Vector displayPos = GeneticRobots.centerOnPlayer(position);
    appearence.render(displayPos, direction.angle());
    lifeBar.draw(displayPos.plus(new Vector(0.0, 0.03)), life / (double)maxLife);
  }
}

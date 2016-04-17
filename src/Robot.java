import java.awt.Color;

public class Robot extends MovingObject implements IShooter, Comparable<Robot>
{
  private final double damage;
  private double inflictedDamage;
  private double timeLived;
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
    this.inflictedDamage = 0;
    this.timeLived = 0;
    this.maxLife = life;
    this.life = this.maxLife;
    this.fireFreq = fireFreq;
    this.lifeBar = new LifeBar(0.03, 0.002);
    this.direction = new Vector(Math.random() - 0.5, Math.random() - 0.5);
    this.color = color;
    this.appearence = new RobotBody(this.color, 0.032, new Vector(2), 0);
    this.collider = new Collider(0.025, position, Tag.ROBOT, this);
    GeneticRobots.addCollider(this.collider);
  }

  public void loseLife(double lifeLost)
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
    Explosion boom = new Explosion(this.position);
    GeneticRobots.addObject(boom);
  }

  public boolean isDead()
  {
    return this.life <= 0;
  }

  public void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - this.lastShot < this.fireFreq)
      return;
    Shot shot = new Shot(this.position, direction.times(0.021), this.damage, Tag.ROBOTSHOT, this.color, this);
    GeneticRobots.addObject(shot);
    this.lastShot = System.currentTimeMillis();
  }

  public void reward(double damage)
  {
    inflictedDamage += damage;
  }

  public int compareTo(Robot that)
  {
    if (this.inflictedDamage > that.inflictedDamage)
      return (int)(this.inflictedDamage - that.inflictedDamage) + 1;
    return (int)(this.timeLived - that.timeLived);
  }

  public double getLife()
  {
    return this.life;
  }

  public double getMaxLife()
  {
    return this.maxLife;
  }

  public double getMaxSpeed()
  {
    return this.maxSpeed;
  }

  public double getDamage()
  {
    return this.damage;
  }

  public double getHitForce()
  {
    return this.damage * 5;
  }

  public double getFireFreq()
  {
    return this.fireFreq;
  }

  public void update(long elapsedTime)
  {
    if (life <= 0)
      die();
    else
    {
      this.timeLived += elapsedTime;
      Vector oldPosition = this.position;
      this.elapsedTime = elapsedTime;
      Vector aim = GeneticRobots.getPlayer().getPosition().minus(position);
      this.goingRight = (aim.cartesian(0) > 0);
      this.goingLeft = (aim.cartesian(0) < 0);
      this.goingUp = (aim.cartesian(1) > 0);
      this.goingDown = (aim.cartesian(1) < 0);
      shoot(this.direction);
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
            double bloodAngle = collision.getForce().times(1).angle();
            Blood blood = new Blood(this.position, bloodAngle, this.color);
            GeneticRobots.addObject(blood);
          }
          else
          {
            this.position = oldPosition;
            move(collision.getForce());
          }
        }
      }
      this.collider.setPosition(this.position);
    }
  }

  public void render()
  {
    Vector displayPos = GeneticRobots.centerOnPlayer(position);
    appearence.render(displayPos, direction.angle());
    lifeBar.draw(displayPos.plus(new Vector(0.0, 0.03)), life / (double)maxLife);
  }
}

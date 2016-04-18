import java.awt.Color;

/**
 * This class defines the behaviour of the robots and their properties.
 * @see MovingObject
 * @see IShooter
 * @see Comparable
 */
public final class Robot extends MovingObject
                         implements IShooter, Comparable<Robot>
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

  /**
   * Create a new robot at the given pos, with the given capacity to accelerate,
   * the given maximum speed, damage, life, fire frequency and color.
   * @param pos the positino to create the robot
   * @param speedUp the capacity to accelerate
   * @param maxSpeed the maximum speed
   * @param damage the damage this robot will inflict
   * @param life the number of life points
   * @param fireFreq the fire frequency
   * @param color the color
   */
  public Robot(Vector pos, double speedUp, double maxSpeed, double damage,
      double life, double fireFreq, Color color)
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
    this.appearence = new RobotBody(this.color, 0.032);
    this.collider = new Collider(0.025, position, Tag.ROBOT, this);
    GeneticRobots.addCollider(this.collider);
  }

  /**
   * Lose the given amount of life points.
   * @param lifeLost life points to lose
   */
  public void loseLife(double lifeLost)
  {
    this.life -= lifeLost;
    if (this.life < 0)
      this.life = 0;
  }

  // destroy this robot and play the explosion
  private void die()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
    GeneticRobots.getPlayer().incrementScore();
    Explosion explosion = new Explosion(this.position);
    GeneticRobots.addObject(explosion);
  }

  /**
   * State whether or not thi robot is dead.
   * @return true if his life is positive, false otherwise
   */
  public boolean isDead()
  {
    return this.life <= 0;
  }

  /**
   * {@inheritDoc}
   */
  public void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - this.lastShot < 1 / this.fireFreq)
      return;
    Shot shot = new Shot(this.position, direction.times(0.021), this.damage,
        Tag.ROBOTSHOT, this.color, this);
    GeneticRobots.addObject(shot);
    this.lastShot = System.currentTimeMillis();
  }

  /**
   * {@inheritDoc}
   */
  public void reward(double damage)
  {
    inflictedDamage += damage;
  }

  /**
   * {@inheritDoc}
   */
  public int compareTo(Robot that)
  {
    if (this.inflictedDamage > that.inflictedDamage)
      return (int)(this.inflictedDamage - that.inflictedDamage) + 1;
    return (int)(this.timeLived - that.timeLived);
  }

  /**
   * Get the current amount of life point of this robot.
   */
  public double getLife()
  {
    return this.life;
  }

  /**
   * Get the maximum amount of life points this robot can have.
   */
  public double getMaxLife()
  {
    return this.maxLife;
  }

  /**
   * Get the maximum speed of this robot.
   */
  public double getMaxSpeed()
  {
    return this.maxSpeed;
  }

  /**
   * Get the damage this robot can inflict.
   */
  public double getDamage()
  {
    return this.damage;
  }

  /**
   * Get the force this robot will use to hit the player.
   */
  public double getHitForce()
  {
    return this.damage * 5;
  }

  /**
   * Get the fire frequency.
   */
  public double getFireFreq()
  {
    return this.fireFreq;
  }

  /**
   * {@inheritDoc}
   */
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
      if (!this.speed.isZero())
        brake();
      accelerate(aim);
      move();
      Collision collision;
      if ((collision = this.collider.isColliding()) != null)
      {
        Tag tag = collision.getTag();
        if (tag != Tag.ROBOTSHOT && tag != Tag.BONUS)
        {
          if (tag == Tag.PLAYERSHOT)
          {
            // take shot and display blood
            Shot shot = (Shot)(collision.getObject());
            loseLife(shot.getDamage());
            shot.destroy();
            double bloodAngle = collision.getForce().angle();
            Blood blood = new Blood(this.position, bloodAngle, this.color);
            GeneticRobots.addObject(blood);
          }
          else
          {
            // move according to this collision
            this.position = oldPosition;
            move(collision.getForce());
          }
        }
      }
      this.collider.setPosition(this.position);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void render()
  {
    Vector displayPos = GeneticRobots.centerOnPlayer(this.position);
    this.appearence.render(displayPos, this.direction.angle());
    this.lifeBar.draw(displayPos.plus(new Vector(0.0, 0.03)),
        this.life / (double)this.maxLife);
  }
}

import java.awt.event.KeyEvent;
import java.awt.Color;

/**
 * This class defines the behaviour of the player, and all its properties.
 * @see MovingObject
 * @see IShooter
 */
public class Player extends MovingObject implements IShooter
{
  private final Score score;
  private final Collider collider;
  private double inflictedDamage;
  private final double maxLife;
  private double life;
  private final LifeBar lifeBar;
  private boolean isShooting;
  private long lastShot;
  private boolean lookingUp, lookingDown, lookingLeft, lookingRight;
  private Vector lookingDirection;

  /**
   * Create a new Player with the given pseudo.
   * @param pseudo the pseudo of the player.
   */
  public Player(String pseudo)
  {
    super();
    this.score = new Score(pseudo);
    this.inflictedDamage = 0;
    this.maxLife = 42;
    this.life = this.maxLife;
    this.lifeBar = new LifeBar(0.3, 0.0032);
    this.position = GeneticRobots.getCenter(); // initial position
    this.lookingDirection = new Vector(0.001, 1);
    this.appearence = new PlayerBody();
    this.collider = new Collider(0.021, this.position, Tag.PLAYER, this);
    GeneticRobots.addCollider(this.collider);
  }

  // read the key pressed and decide which way to go, to look, and if he shoots
  private void processCommands()
  {
    this.goingUp = SteveDraw.isKeyPressed(KeyEvent.VK_UP);
    this.goingDown = SteveDraw.isKeyPressed(KeyEvent.VK_DOWN);
    this.goingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_LEFT);
    this.goingRight = SteveDraw.isKeyPressed(KeyEvent.VK_RIGHT);
    this.isShooting = SteveDraw.isKeyPressed(KeyEvent.VK_SPACE);
    this.lookingUp = SteveDraw.isKeyPressed(KeyEvent.VK_W);
    this.lookingUp = this.lookingUp || SteveDraw.isKeyPressed(KeyEvent.VK_Z);
    this.lookingDown = SteveDraw.isKeyPressed(KeyEvent.VK_S);
    this.lookingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_A);
    this.lookingLeft = this.lookingLeft
      || SteveDraw.isKeyPressed(KeyEvent.VK_Q);
    this.lookingRight = SteveDraw.isKeyPressed(KeyEvent.VK_D);
  }

  /**
   * Get the current score of the player.
   */
  public Score getScore()
  {
    return this.score;
  }

  /**
   * Increment the current score of 1 kill.
   */
  public void incrementScore()
  {
    this.score.increment();
  }

  /**
   * Get the current life of the player.
   */
  public double getLife()
  {
    return this.life;
  }

  /**
   * Get the maximum life the player can take.
   */
  public double getMaxLife()
  {
    return this.maxLife;
  }

  // modify the current life depending on the given value
  private void affectLife(double lifeEffect)
  {
    this.life += lifeEffect;
    if (this.life > this.maxLife)
      this.life = this.maxLife;
    if (this.life < 0)
      this.life = 0;
  }

  // kill the player and loose the game
  private void die()
  {
    GeneticRobots.lose(this.score);
  }

  /**
   * {@inheritDoc}
   */
  public void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - this.lastShot < 100)
      return;
    Vector gun = this.position.plus(direction.times(0.05));
    Shot shot = new Shot(gun, direction.times(0.021), 1, Tag.PLAYERSHOT,
        this);
    GeneticRobots.addObject(shot);
    this.lastShot = System.currentTimeMillis();
  }

  /**
   * {@inheritDoc}
   */
  public void reward(double damage)
  {
    this.inflictedDamage += damage;
  }

  /**
   * Rotate the player's gun depending on where he looks.
   */
  private void rotate(long elapsedTime)
  {
    double[] rotation = { 0, 0 };
    rotation[0] += this.lookingRight ? 1 : 0;
    rotation[0] -= this.lookingLeft ? 1 : 0;
    rotation[1] += this.lookingUp ? 1 : 0;
    rotation[1] -= this.lookingDown ? 1 : 0;
    Vector rot = new Vector(rotation);
    if (rot.isZero())
      return;
    rot = rot.direction().times(0.1 * elapsedTime);
    this.lookingDirection = this.lookingDirection.plus(rot).direction();
  }

  /**
   * {@inheritDoc}
   */
  public void update(long elapsedTime)
  {
    if (this.life <= 0)
      die();
    else
    {
      Vector oldPosition = this.position;
      this.elapsedTime = elapsedTime;
      processCommands();
      rotate(elapsedTime);
      if (this.isShooting)
        shoot(this.lookingDirection);
      if (!this.speed.isZero())
        brake();
      accelerate();
      move();
      Collision collision;
      if ((collision = this.collider.isColliding()) != null)
      {
        Tag tag = collision.getTag();
        if (tag != Tag.PLAYERSHOT)
        {
          boolean robotShot = (tag == Tag.ROBOTSHOT);
          boolean robotColl = (tag == Tag.ROBOT);
          if (robotShot)
          {
            // take the shot
            Shot shot = (Shot)(collision.getObject());
            affectLife(- shot.getDamage());
            shot.destroy();
            shot.rewardShooter();
          }
          else if (tag == Tag.BONUS)
          {
            Bonus bonus = (Bonus)(collision.getObject());
            affectLife(bonus.getLifePoints());
            bonus.destroy();
          }
          else
          {
            // modify the position of the player depending on the collision
            this.position = oldPosition;
            move(collision.getForce());
          }
          if (robotColl)
          {
            // being hitten by the robot
            Robot robot = (Robot)collision.getObject();
            affectLife(- robot.getLife());
            robot.loseLife(robot.getLife());
            robot.reward(robot.getLife());
          }
          if(robotColl || robotShot)
          {
            // display blood
            double bloodAngle = collision.getForce().angle();
            Blood blood = new Blood(this.position, bloodAngle, Color.white);
            GeneticRobots.addObject(blood);
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
    // player is always in the center of the screen
    this.appearence.render(GeneticRobots.getCenter(),
        this.lookingDirection.angle());
    this.lifeBar.draw(new Vector(0.68, 0.98), this.life / (double)this.maxLife);
  }
}

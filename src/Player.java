import java.awt.event.KeyEvent;
import java.awt.Color;

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
    this.appearence = new PlayerBody(Color.black, 0.042, new Vector(2), 0);
    this.collider = new Collider(0.021, this.position, Tag.PLAYER, this);
    GeneticRobots.addCollider(this.collider);
  }

  private void processCommands()
  {
    this.goingUp = SteveDraw.isKeyPressed(KeyEvent.VK_UP);
    this.goingDown = SteveDraw.isKeyPressed(KeyEvent.VK_DOWN);
    this.goingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_LEFT);
    this.goingRight = SteveDraw.isKeyPressed(KeyEvent.VK_RIGHT);
    this.isShooting = SteveDraw.isKeyPressed(KeyEvent.VK_SPACE);
    this.lookingUp = SteveDraw.isKeyPressed(KeyEvent.VK_W);
    this.lookingDown = SteveDraw.isKeyPressed(KeyEvent.VK_S);
    this.lookingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_A);
    this.lookingRight = SteveDraw.isKeyPressed(KeyEvent.VK_D);
    /**********************************
     * TODO REMOVE DVORAK-FR CONFIG
     **********************************/
    lookingUp = SteveDraw.isKeyPressed(KeyEvent.VK_QUOTE);
    lookingDown = SteveDraw.isKeyPressed(KeyEvent.VK_A);
    lookingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_O);
    lookingRight = SteveDraw.isKeyPressed(KeyEvent.VK_U);
    /**********************************
     * TODO REMOVE DVORAK-FR CONFIG
     **********************************/
  }

  public Score getScore()
  {
    return this.score;
  }

  public void incrementScore()
  {
    this.score.increment();
  }

  public double getLife()
  {
    return this.life;
  }

  public double getMaxLife()
  {
    return this.maxLife;
  }

  private void affectLife(double lifeEffect)
  {
    this.life += lifeEffect;
    if (this.life > this.maxLife)
      this.life = this.maxLife;
    if (this.life < 0)
      this.life = 0;
  }

  private void die()
  {
    GeneticRobots.lose(this.score);
  }

  public void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - lastShot < 100)
      return;
    Vector gun = position.plus(direction.times(0.05));
    Shot shot = new Shot(gun, direction.times(0.021), 1, Tag.PLAYERSHOT, this);
    GeneticRobots.addObject(shot);
    lastShot = System.currentTimeMillis();
  }

  public void reward(double damage)
  {
    this.inflictedDamage += damage;
  }

  private void rotate(long elapsedTime)
  {
    double[] rotation = { 0, 0 };
    rotation[0] += lookingRight ? 1 : 0;
    rotation[0] -= lookingLeft ? 1 : 0;
    rotation[1] += lookingUp ? 1 : 0;
    rotation[1] -= lookingDown ? 1 : 0;
    Vector rot = new Vector(rotation);
    if (rot.isZero())
      return;
    rot = rot.direction().times(0.1 * elapsedTime);
    lookingDirection = lookingDirection.plus(rot).direction();
  }

  public void update(long elapsedTime)
  {
    if (life <= 0)
      die();
    else
    {
      Vector oldPosition = this.position;
      this.elapsedTime = elapsedTime;
      processCommands();
      rotate(elapsedTime);
      if (isShooting)
        shoot(lookingDirection);
      if (!speed.isZero())
        brake();
      accelerate();
      move();
      Collision collision;
      if ((collision = collider.isColliding()) != null)
      {
        Tag tag = collision.getTag();
        if (tag != Tag.PLAYERSHOT)
        {
          if (tag == Tag.ROBOTSHOT)
          {
            Shot shot = (Shot)(collision.getObject());
            affectLife(- shot.getDamage());
            shot.destroy();
            shot.rewardShooter();
            double bloodAngle = collision.getForce().times(-1).angle();
            Blood blood = new Blood(this.position, bloodAngle, Color.white);
            GeneticRobots.addObject(blood);
          }
          else if (tag == Tag.BONUS)
          {
            Bonus bonus = (Bonus)(collision.getObject());
            affectLife(bonus.getLifePoints());
            bonus.destroy();
          }
          else
          {
            this.position = oldPosition;
            move(collision.getForce());
          }
          if (tag == Tag.ROBOT)
          {
            Robot robot = (Robot)collision.getObject();
            affectLife(- robot.getLife());
            robot.loseLife(robot.getLife());
            robot.reward(robot.getLife());
          }
        }
      }
      this.collider.setPosition(this.position);
    }
  }

  public void render()
  {
    // player is always in the center of the screen
    appearence.render(GeneticRobots.getCenter(), lookingDirection.angle());
    lifeBar.draw(new Vector(0.68, 0.98), life / (double)maxLife);
  }
}

import java.awt.event.KeyEvent;
import java.awt.Color;

public class Player extends MovingObject
{
  private final Collider collider;
  private final long maxLife;
  private long life;
  private final LifeBar lifeBar;
  private boolean isShooting;
  private long lastShot;
  private boolean lookingUp, lookingDown, lookingLeft, lookingRight;
  private Vector lookingDirection;

  public Player()
  {
    super();
    this.maxLife = 42;
    this.life = this.maxLife;
    this.lifeBar = new LifeBar(0.3, 0.0032);
    this.position = GeneticRobots.getCenter(); // initial position
    this.lookingDirection = this.direction;
    this.appearence = new PlayerBody(Color.black, 0.042, new Vector(2), 0);
    this.collider = new Collider(0.021, this.position, "player", this);
    GeneticRobots.addCollider(this.collider);
  }

  private void processCommands()
  {
    goingUp = SteveDraw.isKeyPressed(KeyEvent.VK_UP);
    goingDown = SteveDraw.isKeyPressed(KeyEvent.VK_DOWN);
    goingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_LEFT);
    goingRight = SteveDraw.isKeyPressed(KeyEvent.VK_RIGHT);
    isShooting = SteveDraw.isKeyPressed(KeyEvent.VK_SPACE);
    lookingUp = SteveDraw.isKeyPressed(KeyEvent.VK_W);
    lookingDown = SteveDraw.isKeyPressed(KeyEvent.VK_S);
    lookingLeft = SteveDraw.isKeyPressed(KeyEvent.VK_A);
    lookingRight = SteveDraw.isKeyPressed(KeyEvent.VK_D);
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

  public long getLife()
  {
    return this.life;
  }

  public long getMaxLife()
  {
    return this.maxLife;
  }

  private void loseLife(long lifeLost)
  {
    this.life -= lifeLost;
  }

  private void die()
  {
    GeneticRobots.lose();
  }

  private void shoot(Vector direction)
  {
    if (System.currentTimeMillis() - lastShot < 100)
      return;
    Vector gun = position.plus(direction.times(0.05));
    Shot shot = new Shot(gun, direction.times(0.021), 1, "playerShot");
    GeneticRobots.addObject(shot);
    lastShot = System.currentTimeMillis();
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
        String tag = collision.getTag();
        if (tag != "playerShot")
        {
          if (tag == "robotShot")
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
    // player is always in the center of the screen
    appearence.render(GeneticRobots.getCenter(), lookingDirection.angle());
    lifeBar.draw(new Vector(0.68, 0.98), life / (double)maxLife);
  }
}

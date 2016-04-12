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

  public Player()
  {
    super();
    this.maxLife = 42;
    this.life = this.maxLife;
    this.lifeBar = new LifeBar(0.3, 0.0032);
    this.position = GeneticRobots.getCenter(); // initial position
    this.appearence = new PlayerBody(Color.black, 0.042, new Vector(2), 0);
    this.collider = new Collider(0.021, this.position, "player", this);
    GeneticRobots.addCollider(this.collider);
  }

  private void processCommands()
  {
    isUp = SteveDraw.isKeyPressed(KeyEvent.VK_UP);
    isDown = SteveDraw.isKeyPressed(KeyEvent.VK_DOWN);
    isLeft = SteveDraw.isKeyPressed(KeyEvent.VK_LEFT);
    isRight = SteveDraw.isKeyPressed(KeyEvent.VK_RIGHT);
    isShooting = SteveDraw.isKeyPressed(KeyEvent.VK_SPACE);
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
    Shot shot = new Shot(position, direction.times(0.021), 1, "playerShot");
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
      processCommands();
      if (isShooting)
        shoot(direction);
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
            //loseLife(shot.getDamage());
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
    appearence.render(GeneticRobots.getCenter(), direction.angle());
    lifeBar.draw(new Vector(0.69, 0.98), life / (double)maxLife);
  }
}

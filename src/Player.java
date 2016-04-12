import java.awt.event.KeyEvent;
import java.awt.Color;

public class Player extends MovingObject
{
  private final Collider collider;
  private long life;
  private boolean isShooting;
  private long lastShot;

  public Player()
  {
    super();
    life = 42;
    position = GeneticRobots.getCenter(); // initial position
    appearence = new PlayerBody(Color.black, 0.042, new Vector(2), 0);
    collider = new Collider(0.021, position, "player", this);
    GeneticRobots.addCollider(collider);
  }

  private void processCommands()
  {
    isUp = SteveDraw.isKeyPressed(KeyEvent.VK_UP);
    isDown = SteveDraw.isKeyPressed(KeyEvent.VK_DOWN);
    isLeft = SteveDraw.isKeyPressed(KeyEvent.VK_LEFT);
    isRight = SteveDraw.isKeyPressed(KeyEvent.VK_RIGHT);
    isShooting = SteveDraw.isKeyPressed(KeyEvent.VK_SPACE);
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
        if (collision.getTag() != "playerShot")
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
    // player is always in the center of the screen
    appearence.render(GeneticRobots.getCenter(), direction.angle());
  }
}

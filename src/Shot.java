import java.awt.Color;

public class Shot extends MovingObject
{
  private final Collider collider;
  private final long damage;

  public Shot(Vector position, Vector speed, int damage, String tag)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    appearence = new PlayerBullet(new Vector(2), 0);
  }

  public Shot(Vector position, Vector speed, int damage, String tag, Color color)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    appearence = new RobotBullet(new Vector(2), 0, color);
  }

  public void destroy()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  public void update(long elasedTime)
  {
    double distance = position.minus(GeneticRobots.getPlayer().getPosition()).magnitude();
    if (distance > 1)
      this.destroy();
    else
    {
      this.elapsedTime = elapsedTime;
      move();
      collider.setPosition(this.position);
    }
  }

  public long getDamage()
  {
    return this.damage;
  }

  public void render()
  {
    appearence.render(GeneticRobots.centerOnPlayer(position), direction.angle());
  }
}

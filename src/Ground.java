public class Ground extends FixedObject
{
  public Ground()
  {
    position = new Vector(0, 0);
    appearence = new Background();
  }

  public void render()
  {
    appearence.render(GeneticRobots.centerOnPlayer(position), 0);
  }

  public void update(long elapsedTime)
  {
    return;
  }
}

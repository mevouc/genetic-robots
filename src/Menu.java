import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Menu
{
  private static int bgR, bgG, bgB;
  private static String instructions;
  private static LeaderBoard leaderBoard;

  static
  {
    bgR = 5;
    bgG = 5;
    bgB = 15;
    instructions = "Kill as many Robots\nas you can but\n watch your time!\n"
                 + "The Robots will evolve\nbetween each wave.\n"
                 + "Try to get bonuses if\nyou are injured.";
    leaderBoard = new LeaderBoard("../leaderboard");
  }

  private static void base(Color background)
  {
    SteveDraw.clear(background);
    double y = GeneticRobots.canvasH * 0.95 / GeneticRobots.canvasH;
    SteveDraw.setPenColor(new Color(186, 22, 44));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
    SteveDraw.text(0.5, y, "GENETIC ROBOTS");
  }

  private static void generalMenu()
  {
    SteveDraw.setPenColor(Color.lightGray);
    double y = GeneticRobots.canvasH * 0.75 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    SteveDraw.text(0.5, y, "PLAY:\nPress ENTER");
    y = GeneticRobots.canvasH * 0.50 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "INSTRUCTIONS:\nPress SPACE");
    y = GeneticRobots.canvasH * 0.25 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "QUIT:\nPress ESCAPE");
  }

  private static void instructions()
  {
    base(new Color(bgR, bgG, bgB));
    SteveDraw.setPenColor(Color.gray);
    double y = GeneticRobots.canvasH * 0.85 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
    SteveDraw.text(0.5, y, "INSTRUCTIONS");
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.70 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, instructions);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN:\nPress ESCAPE");
    while (!(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE)))
      continue;
  }

  public static void deathScreen(Score newScore)
  {
    base(new Color(bgR, bgG, bgB, 160));
    SteveDraw.setPenColor(Color.gray);
    double y = GeneticRobots.canvasH * 0.85 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
    SteveDraw.text(0.5, y, "GAME OVER\nLEADERBOARD:");
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.65 / GeneticRobots.canvasH;
    SteveDraw.textRight(0.9, y, leaderBoard.addScore(newScore));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN TO MENU:\nPress ESCAPE");
    SteveDraw.show();
    while (!(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE)))
      continue;
  }

  public static boolean exec()
  {
    base(new Color(bgR, bgG, bgB));
    generalMenu();
    SteveDraw.show();
    boolean waitChoice = true;
    boolean choice = true;
    while (waitChoice)
    {
      if (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
        waitChoice = choice = false;
      else if (SteveDraw.isKeyPressed(KeyEvent.VK_ENTER))
        waitChoice = !(choice = true);
      else if (SteveDraw.isKeyPressed(KeyEvent.VK_SPACE))
      {
        instructions();
        base(new Color(bgR, bgG, bgB));
        generalMenu();
        while (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
          continue;
        SteveDraw.show();
      }
    }
    SteveDraw.setFont();
    return choice;
  }
}

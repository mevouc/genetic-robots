import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Font;

/**
 * This class manage all the menu in the program, including the death screen and
 * leaderboard.
 */
public final class Menu
{
  // the constant values for the menu
  private static final int bgR, bgG, bgB;
  private static final String instructions;
  private static final LeaderBoard leaderBoard;
  private static final Sound music;

  // static initializer
  static
  {
    // default values for the background
    bgR = 5;
    bgG = 5;
    bgB = 15;
    instructions = "Use SPACE to shoot.\n\n"
                 + "Use Arrow Keys to move.\n\n"
                 + "Use WASD or ZQSD to\nchange your shoot angle.\n";
    // path to the file containing the leaderboard
    leaderBoard = new LeaderBoard("leaderboard");
    // http://opengameart.org/content/enchanted-tiki-86
    music = new Sound("snd/enchanted_tiki_86.wav");
  }

  // render the common part of all the menu screens
  private static void base(Color background)
  {
    SteveDraw.clear(background);
    double y = GeneticRobots.canvasH * 0.95 / GeneticRobots.canvasH;
    SteveDraw.setPenColor(new Color(186, 22, 44));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
    SteveDraw.text(0.5, y, "GENETIC ROBOTS");
  }

  // render the general menu
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

  // render the instructions menu, wait for ESCAPE to be pressed before coming
  // back to the general menu
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

  /**
   * Render the death screen and the leaderboard. Call the method to update the
   * leaderboard.
   * @param newScore score to add to the updated leaderboard.
   */
  public static void deathScreen(Score newScore)
  {
    base(new Color(bgR, bgG, bgB, 220));
    SteveDraw.setPenColor(Color.gray);
    double y = GeneticRobots.canvasH * 0.85 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
    SteveDraw.text(0.5, y, "GAME OVER\nLEADERBOARD:");
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.65 / GeneticRobots.canvasH;
    // add the new score to the leaderboard and get a string representing the
    // current leaderboard to be prompted
    SteveDraw.textRight(0.92, y, leaderBoard.addScore(newScore));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN TO MENU:\nPress ESCAPE");
    SteveDraw.show();
    music.play();
    while (!(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE)))
      continue;
  }

  /**
   * Execute the general menu. Wait for inputs to change the menu if necessary.
   * And return the pseudo selected by the user if he wants to play.
   * @return null if the user want to quit the game, the selected pseudo
   * otherwise
   */
  public static String exec()
  {
    base(new Color(bgR, bgG, bgB));
    generalMenu();
    SteveDraw.show();
    // play the background music
    if (!music.isPlaying())
      music.play();
    boolean waitChoice = true;
    String pseudo = null;
    while (waitChoice)
    {
      if (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
        waitChoice = false;
      else if (SteveDraw.isKeyPressed(KeyEvent.VK_ENTER))
      {
        pseudo = askPseudo();
        base(new Color(bgR, bgG, bgB));
        generalMenu();
        while (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
          continue;
        waitChoice = (pseudo == null);
      }
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
    music.stop();
    SteveDraw.setFont();
    return pseudo;
  }

  /**
   * Let the user choose the pseudo he or she wants. It must be less than 14
   * characters to fit in the the leaderboard.
   * @return null if the user want to come back to menu, the selected pseudo
   * otherwise
   */
  public static String askPseudo()
  {
    while (SteveDraw.isKeyPressed(KeyEvent.VK_ENTER))
      continue;
    // empty any character in the buffer which would have been typed while in
    // the general menu
    while (SteveDraw.hasNextKeyTyped())
      SteveDraw.nextKeyTyped();
    Color bg = new Color(bgR, bgG, bgB);
    base(bg);
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    double y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN TO MENU:\nPress ESCAPE");
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.7 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "Enter a pseudo\n(less than 14 characters)\n");
    y = GeneticRobots.canvasH * 0.5 / GeneticRobots.canvasH;
    SteveDraw.filledRectangle(0.5, y, 0.45, 0.05);
    SteveDraw.setPenColor(bg);
    SteveDraw.textLeft(0.10, y, "|");
    String pseudo = "";
    char c = 0;
    boolean ok = false;
    // while the user is choosing a pseudo
    while (!ok)
    {
      // ESCAPE make the user quit the pseudo selection screen
      if (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
      {
        pseudo = null;
      }
      else if (SteveDraw.hasNextKeyTyped()
          && ((c = SteveDraw.nextKeyTyped()) != 27))
      {
        switch (c)
        {
        // the user can delete the last character using the BACKSPACE key
        case '\b':
          if (pseudo.length() > 0)
            pseudo = pseudo.substring(0, pseudo.length() - 1);
          break;
        case '\n':
        case 4:
          break;
        default:
          if (pseudo.length() < 14)
            pseudo += c;
        }
        SteveDraw.setPenColor(Color.lightGray);
        SteveDraw.filledRectangle(0.5, y, 0.45, 0.05);
        SteveDraw.setPenColor(bg);
        SteveDraw.textLeft(0.10, y, pseudo + '|');
        SteveDraw.show();
      }
      // the user can confirm his choice by pressing ENTER or Ctrl-D (EOT)
      ok = (pseudo == null) || c == '\n' || (c == 4);
    }
    return pseudo;
  }
}

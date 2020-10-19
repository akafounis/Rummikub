package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainController;

/**
 * Main-class of the application.
 * Launches the game and starts the GUI.
 */
public class Main extends Application {

  private static final String GAME_NAME = "Rummikub";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(GAME_NAME);
    new MainController(primaryStage).switchToStartScene();
  }
}

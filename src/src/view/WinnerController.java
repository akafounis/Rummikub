package view;

import java.io.IOException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller responsible for the Winner-View.
 * Displays who has won the game.
 */
public class WinnerController {

  @FXML
  private Text rankList;
  @FXML
  private MainController mainController;

  /**
   * Connects the WinnerController to a MainController.
   *
   * @param mainController to be connected to
   */
  void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  @FXML
  private void restartGame() throws IOException {
    mainController.restartGame();
  }

  @FXML
  private void goBackToLobby() throws IOException {
    mainController.switchToStartScene();
    mainController.handleQuitPressed();
    //TODO: Handle IOException
  }

  /**
   * Displays the winner and final ranking of the game.
   *
   * @param finalRank map mapping each player to his points
   */
  void setRank(Map<String, Integer> finalRank) {
    StringBuilder stringBuilder = new StringBuilder();
    int place = 1;
    for (Map.Entry<String, Integer> player : finalRank.entrySet()) {
      stringBuilder.append(place).append(". ");

      String playerName = player.getKey();
      stringBuilder.append(playerName).append(": ");

      Integer playerPoints = player.getValue();
      stringBuilder.append(playerPoints).append(" stone points\n");

      place++;
    }

    rankList.setText(stringBuilder.toString());
  }
}

package view;

import view.music.Audio;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller responsible for the waiting-scene that appears after joining a game.
 */
public class WaitController {

  private MainController mainController;

  @FXML private Text ipAddress;
  @FXML private Text player0;
  @FXML private Text player1;
  @FXML private Text player2;
  @FXML private Text player3;
  @FXML private Button notMuteButton;
  @FXML private Button muteButton;

  /**
   * Sends start request when start game button is clicked.
   */
  @FXML
  private void startGame() {
    mainController.sendStartRequest();
  }

  /**
   * Sets instanced MainController.
   * @param mainController MainController to set
   */
  void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Set and display the names depending on amount of joined players.
   *
   * @param names List of player names which joined
   */
  void setPlayerNames(List<String> names) {
    System.out.println("From WaitCtrl.: setting names.. " + names);

    switch (names.size()) {
      case 4:
        player0.setText(names.get(0));
        player1.setText(names.get(1));
        player2.setText(names.get(2));
        player3.setText(names.get(3));
        break;
      case 3:
        player0.setText(names.get(0));
        player1.setText(names.get(1));
        player2.setText(names.get(2));
        player3.setText(ViewConstants.NO_PLAYER_SYMBOL);
        break;
      case 2:
        player0.setText(names.get(0));
        player1.setText(names.get(1));
        player2.setText(ViewConstants.NO_PLAYER_SYMBOL);
        player3.setText(ViewConstants.NO_PLAYER_SYMBOL);
        break;
      case 1:
        player0.setText(names.get(0));
        player1.setText(ViewConstants.NO_PLAYER_SYMBOL);
        player2.setText(ViewConstants.NO_PLAYER_SYMBOL);
        player3.setText(ViewConstants.NO_PLAYER_SYMBOL);
        break;
    }
  }


  /**
   * Disable sound when mute button is clicked.
   */
  @FXML
  private void mute() {
    Audio.muteSoundOfWait();
    muteButton.setVisible(false);
    notMuteButton.setVisible(true);
  }

  /**
   * Re-activate sound when unmute button is clicked.
   */
  @FXML
  private void unMute() {
    Audio.playMusicNow();
    notMuteButton.setVisible(false);
    muteButton.setVisible(true);
  }

  /**
   * Lets user quit the waiting view when quit button is pressed.
   */
  @FXML
  private void quitWaiting() {
    System.out.println("From QUIT in WaitCtrl.: disconnect client!");
    mainController.handleQuitPressed();
  }

  /**
   * Sets and displays the server IP.
   *
   * @param serverIP IP to be displayed in waiting view
   */
  void setServerIP(String serverIP) {
    ipAddress.setText(serverIP);
  }
}

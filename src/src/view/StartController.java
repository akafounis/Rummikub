package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Class acting as the controller before a game has been started.
 * Controlls the Start-View.
 */
public class StartController {

  /**
   * Enum for the different types of Errors that can occur.
   */
  enum ErrorType {
    IP, AGE, NAME
  }

  private MainController mainController;

  @FXML private TextField ageField;
  @FXML private TextField ipField;
  @FXML private TextField nameField;
  @FXML private Text ageError;
  @FXML private Text ipError;
  @FXML private Text nameError;

  /**
   * Connects the StartController to a MainController.
   *
   * @param mainController to be connected to
   */
  void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Displays error for specified text field.
   *
   * @param error Indicator for invalid text field
   */
  private void setError(ErrorType error) {
    switch (error) {
      case IP:
        ipField.setStyle(ViewConstants.ERROR_STYLE);
        ipError.setVisible(true);
        break;
      case AGE:
        ageField.setStyle(ViewConstants.ERROR_STYLE);
        ageError.setVisible(true);
        break;
      case NAME:
        nameField.setStyle(ViewConstants.ERROR_STYLE);
        nameError.setVisible(true);
        break;
    }
  }

  /**
   * Creates a Player, connects to a server and joins a game.
   */
  @FXML
  void joinGame() {
    clearErrors();
    if (isValidInput()) {
      mainController.initPlayer(ipField.getText(), nameField.getText(), Integer.parseUnsignedInt(ageField.getText()));
    }
  }

  /**
   * Sets up a server, then joins that server.
   */
  @FXML
  private void hostGame() {
    clearErrors();
    if (isValidInput()) {
      if (mainController.startServer()) {
        joinGame();
      }
    }
  }

  /**
   * Resets all (former invalid) text fields in form.
   */
  private void clearErrors() {
    ipField.setStyle(null);
    ipError.setVisible(false);
    nameField.setStyle(null);
    nameError.setVisible(false);
    ageField.setStyle(null);
    ageError.setVisible(false);
  }

  /**
   * Shows help scene when help button is clicked.
   */
  @FXML
  private void showHelpScene() {
    mainController.showHelpScene();
  }

  /**
   * Shows an error-message indicating that no server is available.
   */
  void showNoServerError() {
    setError(ErrorType.IP);
  }

  /**
   * Tests form input for validity.
   * - userName non-empty + up to 20 characters in length
   * - age in between 6 and 150
   *
   * @return whether user input matches the given parameters
   */
  private boolean isValidInput() {
    String userName = nameField.getText();
    boolean isValidInput = true;

    // Test if name is not only spaces and length is 20
    if (userName.trim().isEmpty() || userName.length() > 20) {
      setError(ErrorType.NAME);
      isValidInput = false;
    }

    // Test age input
    try {
      int age = Integer.parseInt(ageField.getText());
      if (age < 6 || age > 150) {
        setError(ErrorType.AGE);
        isValidInput = false;
      }
    } catch (NumberFormatException e) {
      setError(ErrorType.AGE);
      isValidInput = false;
    }
    return isValidInput;
  }
}

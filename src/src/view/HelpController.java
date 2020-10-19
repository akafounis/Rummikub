package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller responsible for the display of the help-message.
 */
public class HelpController {

  @FXML
  private Button okButton;

  @FXML
  private void handleOkButton() {
    ((Stage) okButton.getScene().getWindow()).close();
  }
}

package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/** Controls the main application screen */
public class MainViewController {
  
  @FXML private Button logoutButton;
  @FXML private Label  sessionLabel;
  
  public void initSessionID(final LoginManager loginManager, String sessionID) {
    
	 sessionLabel.setText(sessionID);
	
     logoutButton.setOnAction(e->loginManager.logout());
 
  }
}
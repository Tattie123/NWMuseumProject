package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Button LoginAsStaff;
    @FXML
    private Button LoginAsManager;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleLoginAsStaff() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            ErrorController.showError("Validation Error", "Please enter username and password.");
            return;
        }
        // simple hardcoded authentication placeholder
        if (username.equals("staff") && password.equals("staff")) {
            SceneManager.switchScene("Staff Menu.fxml");
        } else {
            ErrorController.showError("Authentication Failed", "Invalid staff credentials.");
        }
    }

    @FXML
    public void handleLoginAsManager() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            ErrorController.showError("Validation Error", "Please enter username and password.");
            return;
        }
        // simple hardcoded authentication placeholder
        if (username.equals("manager") && password.equals("manager")) {
            SceneManager.switchScene("Manager Menu.fxml");
        } else {
            ErrorController.showError("Authentication Failed", "Invalid manager credentials.");
        }
    }
}

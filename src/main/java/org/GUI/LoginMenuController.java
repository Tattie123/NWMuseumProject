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
        // TODO: Implement staff login authentication
        System.out.println("Logging in as Staff - Username: " + username);
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleLoginAsManager() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        // TODO: Implement manager login authentication
        System.out.println("Logging in as Manager - Username: " + username);
        SceneManager.switchScene("Manager Menu.fxml");
    }
}


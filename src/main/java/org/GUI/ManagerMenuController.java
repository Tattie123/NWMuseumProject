package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManagerMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private Button SetInsurance;
    @FXML
    private Button AddNewRoom;
    @FXML
    private Button ViewAllLoans;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleSetInsurance() {
        SceneManager.switchScene("Insurance Menu.fxml");
    }

    @FXML
    public void handleAddNewRoom() {
        SceneManager.switchScene("Room Menu.fxml");
    }

    @FXML
    public void handleViewAllLoans() {
        SceneManager.switchScene("Loan Menu.fxml");
    }
}


package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class LoanMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ListView<?> LoanListView;

    @FXML
    public void initialize() {
        // TODO: Load loans from database and populate LoanListView
        System.out.println("Initializing Loan Menu...");
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }
}


package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class RequestLoanController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<?> ArtefactComboBox;
    @FXML
    private DatePicker LoanStartDate;
    @FXML
    private DatePicker LoanEndDate;
    @FXML
    private Button RequestLoanButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleRequestLoan() {
        String selectedArtefact = ArtefactComboBox.getValue() != null ? ArtefactComboBox.getValue().toString() : "Unknown";
        String startDate = LoanStartDate.getValue() != null ? LoanStartDate.getValue().toString() : "Not set";
        String endDate = LoanEndDate.getValue() != null ? LoanEndDate.getValue().toString() : "Not set";
        // TODO: Implement loan request functionality
        System.out.println("Requesting loan for artefact: " + selectedArtefact + " from " + startDate + " to " + endDate);
    }
}


package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class InsuranceMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<?> ArtefactComboBox;
    @FXML
    private TextField InsuranceField;
    @FXML
    private Button SetInsuranceButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }

    @FXML
    public void handleSetInsurance() {
        // TODO: Implement set insurance functionality
        String selectedArtefact = ArtefactComboBox.getValue() != null ? ArtefactComboBox.getValue().toString() : "Unknown";
        String insuranceValue = InsuranceField.getText();
        System.out.println("Setting insurance for " + selectedArtefact + ": " + insuranceValue);
        InsuranceField.clear();
    }
}


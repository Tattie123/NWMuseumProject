package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.artefacts.Artefact;

import java.util.List;
import java.util.stream.Collectors;

public class InsuranceMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<String> ArtefactComboBox;
    @FXML
    private TextField InsuranceField;
    @FXML
    private Button SetInsuranceButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }

    @FXML
    public void initialize() {
        loadArtefacts();
    }

    private void loadArtefacts() {
        try {
            Inventory.getInstance().UpdateArtefactsFromDB(false);
            List<Artefact> arts = Inventory.getInstance().getArtifacts();
            if (arts != null) {
                ArtefactComboBox.setItems(FXCollections.observableArrayList(arts.stream().map(Artefact::getName).collect(Collectors.toList())));
            }
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to load artefacts: " + e.getMessage(), e);
        }
    }

    @FXML
    public void handleSetInsurance() {
        String selected = ArtefactComboBox.getValue();
        String val = InsuranceField.getText();
        if (selected == null || val == null || val.isBlank()) {
            ErrorController.showError("Validation Error", "Please select an artefact and enter an insurance value.");
            return;
        }
        try {
            double d = Double.parseDouble(val);
            DataBase.setInsuranceValue(selected, d);
            Inventory.getInstance().UpdateArtefactsFromDB(false);
            InsuranceField.clear();
            ErrorController.showInfo("Success", "Insurance updated.");
        } catch (NumberFormatException nfe) {
            ErrorController.showError("Validation Error", "Insurance must be a number.");
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to set insurance: " + e.getMessage(), e);
        }
    }
}

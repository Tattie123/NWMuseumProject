package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.museum.artefacts.Artefact;
import org.museum.data.Inventory;
import org.museum.other.Loan;
import org.museum.data.DataBase;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RequestLoanController implements Initializable {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<String> ArtefactComboBox;
    @FXML
    private DatePicker LoanStartDate;
    @FXML
    private DatePicker LoanEndDate;
    @FXML
    private Button RequestLoanButton;
    @FXML
    private TextField RequesterNameField;
    @FXML
    private TextField RequesterContactField;
    @FXML
    private TextField RequesterTelField;

    // static holder for preselected artefact coming from other controllers
    private static String preselectedArtefact = null;

    public static void setPreselectedArtefact(String artefactName) {
        preselectedArtefact = artefactName;
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleRequestLoan() {
        String selectedArtefact = ArtefactComboBox.getValue();
        if (selectedArtefact == null) {
            ErrorController.showError("Validation Error", "Please select an artefact.");
            return;
        }
        if (LoanStartDate.getValue() == null || LoanEndDate.getValue() == null) {
            ErrorController.showError("Validation Error", "Please select start and end dates.");
            return;
        }

        String requesterName = (RequesterNameField == null || RequesterNameField.getText() == null) ? "Requester" : RequesterNameField.getText().trim();
        String requesterContact = (RequesterContactField == null || RequesterContactField.getText() == null) ? "email@example.com" : RequesterContactField.getText().trim();
        String requesterTel = (RequesterTelField == null || RequesterTelField.getText() == null) ? "" : RequesterTelField.getText().trim();

        if (requesterName.isBlank() || requesterContact.isBlank()) {
            ErrorController.showError("Validation Error", "Please provide requester name and contact info.");
            return;
        }

        try {
            Date start = Date.valueOf(LoanStartDate.getValue());
            Date end = Date.valueOf(LoanEndDate.getValue());
            Loan loan = new Loan(false, requesterName, requesterContact, requesterTel, selectedArtefact, start, end);
            boolean ok = DataBase.addLoan(loan, false);
            if (ok) {
                ErrorController.showInfo("Success", "Loan request submitted.");
                // clear fields
                if (RequesterNameField != null) RequesterNameField.clear();
                if (RequesterContactField != null) RequesterContactField.clear();
                if (RequesterTelField != null) RequesterTelField.clear();
                LoanStartDate.setValue(null);
                LoanEndDate.setValue(null);
            } else {
                ErrorController.showError("DB Error", "Unable to submit loan request.");
            }
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to submit loan request: " + e.getMessage(), e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // populate artefact combo box from Inventory
            Inventory.getInstance().UpdateArtefactsFromDB(false);
            List<Artefact> artefacts = Inventory.getInstance().getArtifacts();
            if (artefacts != null) {
                List<String> names = artefacts.stream().map(Artefact::getName).collect(Collectors.toList());
                ArtefactComboBox.setItems(FXCollections.observableArrayList(names));

                if (preselectedArtefact != null && names.contains(preselectedArtefact)) {
                    ArtefactComboBox.getSelectionModel().select(preselectedArtefact);
                }
            }
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to load artefacts for loan request: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}

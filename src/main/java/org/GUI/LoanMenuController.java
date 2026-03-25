package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.museum.data.Inventory;
import org.museum.other.Loan;

import java.util.List;

public class LoanMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ListView<String> LoanListView;

    @FXML
    public void initialize() {
        loadLoans();
    }

    private void loadLoans() {
        try {
            List<Loan> loans = Inventory.getLoans(false);
            if (loans != null) {
                var items = loans.stream().map(l -> (l.getArtefactName() + " - " + l.getName() + " (" + (l.isApproved() ? "Approved" : "Pending") + ")")).toList();
                LoanListView.setItems(FXCollections.observableArrayList(items));
            }
        } catch (Exception e) {
            System.err.println("Unable to load loans: " + e.getMessage());
        }
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }
}

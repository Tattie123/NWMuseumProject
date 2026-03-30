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
    private Button ApproveButton;
    @FXML
    private Button UnapproveButton;
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

    @FXML
    public void handleApprove() {
        String sel = LoanListView.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        // items formatted as: artefactName + " - " + requester + " (...)")
        String artefactName = sel.split(" - ")[0].trim();
        try {
            Loan loan = Inventory.getInstance().getLoanByArtefactName(artefactName, false);
            if (loan != null) {
                loan.setApproved(true);
                org.museum.data.DataBase.updateLoanApproval(loan.getArtefactName(), true, false);
                // reload from DB to reflect changes
                loadLoans();
            }
        } catch (Exception e) {
            System.err.println("Unable to approve loan: " + e.getMessage());
        }
    }

    @FXML
    public void handleUnapprove() {
        String sel = LoanListView.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        String artefactName = sel.split(" - ")[0].trim();
        try {
            Loan loan = Inventory.getInstance().getLoanByArtefactName(artefactName, false);
            if (loan != null) {
                loan.setApproved(false);
                org.museum.data.DataBase.updateLoanApproval(loan.getArtefactName(), false, false);
                loadLoans();
            }
        } catch (Exception e) {
            System.err.println("Unable to unapprove loan: " + e.getMessage());
        }
    }
}

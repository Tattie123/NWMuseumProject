package org.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.artefacts.Artefact;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javafx.scene.Scene;

public class ArtefactDetailsController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ImageView thumbnail;
    @FXML
    private Button viewImagesButton;
    @FXML
    private Button requestLoanButton;
    @FXML
    private Button closeButton;

    private Artefact artefact;

    public static void showFor(Artefact artefact) {
        try {
            FXMLLoader loader = new FXMLLoader(ArtefactDetailsController.class.getResource("/GUI/Artefact Details.fxml"));
            Parent root = loader.load();
            ArtefactDetailsController controller = loader.getController();
            controller.setArtefact(artefact);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Artefact Details - " + artefact.getName());
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (IOException e) {
            ErrorController.showError("Error", "Unable to open artefact details: " + e.getMessage(), e);
        }
    }

    public void setArtefact(Artefact a) {
        this.artefact = a;
        nameLabel.setText(a.getName());
        typeLabel.setText(a.getType() == null ? "-" : a.getType());
        locationLabel.setText(a.getCurrentRoom() == null ? "-" : a.getCurrentRoom());
        descriptionArea.setText(a.toString());

        // try to load a thumbnail (first image)
        try {
            List<BufferedImage> imgs = DataBase.getImageFromArtefact(a.getName(), false);
            if (imgs != null && !imgs.isEmpty()) {
                BufferedImage bi = imgs.get(0);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();
                InputStream is = new ByteArrayInputStream(imageBytes);
                Image fx = new Image(is);
                thumbnail.setImage(fx);
            }
        } catch (Exception e) {
            // ignore thumbnail failures - not critical
        }

        // wire buttons
        viewImagesButton.setOnAction(evt -> {
            try {
                var images = DataBase.getImageFromArtefact(a.getName(), false);
                if (images == null || images.isEmpty()) {
                    ErrorController.showError("No Images", "No images found for artefact: " + a.getName());
                } else {
                    Inventory.getInstance().ViewImagesOfArtefact(images);
                }
            } catch (Exception e) {
                ErrorController.showError("Image Error", "Unable to load images: " + e.getMessage(), e);
            }
        });

        requestLoanButton.setOnAction(evt -> {
            try {
                RequestLoanController.setPreselectedArtefact(a.getName());
                SceneManager.switchScene("Request Loan.fxml");
                // close this dialog after switching
                Stage s = (Stage) closeButton.getScene().getWindow();
                s.close();
            } catch (Exception e) {
                ErrorController.showError("Request Loan Error", "Unable to open Request Loan: " + e.getMessage(), e);
            }
        });

        closeButton.setOnAction(evt -> {
            Stage s = (Stage) closeButton.getScene().getWindow();
            s.close();
        });
    }
}


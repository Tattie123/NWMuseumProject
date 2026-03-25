package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.museum.data.DataBase;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class ViewAllImagesController {
    @FXML
    private Button BackButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private Button FitButton;
    @FXML
    private Button NextButton;
    @FXML
    private Button PreviousButton;
    @FXML
    private Button ResetZoomButton;

    private final List<Image> images = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {
        loadAllImages();
    }

    private void loadAllImages() {
        try {
            List<BufferedImage> bis = DataBase.getAllImages(false);
            if (bis != null) {
                for (BufferedImage bi : bis) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", baos);
                    baos.flush();
                    byte[] bytes = baos.toByteArray();
                    baos.close();
                    Image fx = new Image(new ByteArrayInputStream(bytes));
                    images.add(fx);
                }
            }
            if (!images.isEmpty()) showImage(0);
        } catch (Exception e) {
            System.err.println("Unable to load images: " + e.getMessage());
        }
    }

    private void showImage(int i) {
        if (images.isEmpty()) return;
        index = (i + images.size()) % images.size();
        ImageView.setImage(images.get(index));
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleFit() {
        if (ImageView.getImage() == null) return;
        ImageView.setPreserveRatio(true);
        ImageView.setFitWidth(600);
        ImageView.setFitHeight(400);
    }

    @FXML
    public void handleNext() {
        if (images.isEmpty()) return;
        showImage(index + 1);
    }

    @FXML
    public void handlePrevious() {
        if (images.isEmpty()) return;
        showImage(index - 1);
    }

    @FXML
    public void handleResetZoom() {
        ImageView.setFitWidth(0);
        ImageView.setFitHeight(0);
        ImageView.setPreserveRatio(false);
    }
}

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

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

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

    // interactive transform state
    private double scale = 1.0;
    private double translateX = 0.0;
    private double translateY = 0.0;

    // drag tracking
    private double dragStartX;
    private double dragStartY;
    private double dragStartTranslateX;
    private double dragStartTranslateY;

    @FXML
    public void initialize() {
        loadAllImages();

        // install interaction handlers on the ImageView
        ImageView.setOnScroll((ScrollEvent se) -> {
            if (ImageView.getImage() == null) return;
            double delta = se.getDeltaY();
            double factor = Math.pow(1.0015, delta); // smooth zoom

            // mouse coordinates relative to image view
            double mx = se.getX();
            double my = se.getY();

            // image coordinates before scale
            double imgX = (mx - translateX) / scale;
            double imgY = (my - translateY) / scale;

            scale = clamp(scale * factor, 0.05, 10.0);

            // adjust translate so the point under the mouse remains under the mouse after scaling
            translateX = mx - imgX * scale;
            translateY = my - imgY * scale;

            updateTransforms();
            se.consume();
        });

        ImageView.setOnMousePressed((MouseEvent me) -> {
            if (me.isPrimaryButtonDown()) {
                dragStartX = me.getX();
                dragStartY = me.getY();
                dragStartTranslateX = translateX;
                dragStartTranslateY = translateY;
            }
        });

        ImageView.setOnMouseDragged((MouseEvent me) -> {
            if (me.isPrimaryButtonDown()) {
                double dx = me.getX() - dragStartX;
                double dy = me.getY() - dragStartY;
                translateX = dragStartTranslateX + dx;
                translateY = dragStartTranslateY + dy;
                updateTransforms();
            }
        });

        ImageView.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                handleFit();
            }
        });
    }

    private double clamp(double v, double min, double max) {
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }

    private void updateTransforms() {
        ImageView.setScaleX(scale);
        ImageView.setScaleY(scale);
        ImageView.setTranslateX(translateX);
        ImageView.setTranslateY(translateY);
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

        // reset interactive transforms for new image
        scale = 1.0;
        translateX = 0.0;
        translateY = 0.0;
        updateTransforms();
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleFit() {
        if (ImageView.getImage() == null) return;
        fitImageToView();
    }

    private void fitImageToView() {
        Image img = ImageView.getImage();
        if (img == null) return;

        double iw = img.getWidth();
        double ih = img.getHeight();

        double vw = ImageView.getFitWidth() <= 0 ? ImageView.getParent().getLayoutBounds().getWidth() : ImageView.getFitWidth();
        double vh = ImageView.getFitHeight() <= 0 ? ImageView.getParent().getLayoutBounds().getHeight() : ImageView.getFitHeight();

        // fallback to parent's size if fitWidth/fitHeight not used
        if (Double.isNaN(vw) || vw <= 0) vw = ImageView.getParent().getLayoutBounds().getWidth();
        if (Double.isNaN(vh) || vh <= 0) vh = ImageView.getParent().getLayoutBounds().getHeight();

        if (vw <= 0 || vh <= 0) {
            // cannot compute yet; do nothing
            return;
        }

        double sx = vw / iw;
        double sy = vh / ih;
        scale = Math.max(0.01, Math.min(10.0, Math.min(sx, sy) * 0.95));

        // center image
        translateX = (vw - iw * scale) / 2.0;
        translateY = (vh - ih * scale) / 2.0;
        updateTransforms();
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
        scale = 1.0;
        translateX = 0.0;
        translateY = 0.0;
        updateTransforms();
    }
}

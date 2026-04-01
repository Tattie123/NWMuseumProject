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

    // when another controller wants to show a specific set of images we keep them here
    private static List<java.awt.image.BufferedImage> pendingBufferedImages = null;

    // interactive transform state
    private double scale = 1.0;
    private double translateX = 0.0;
    private double translateY = 0.0;

    // drag tracking
    private double dragStartX;
    private double dragStartY;
    private double dragStartTranslateX;
    private double dragStartTranslateY;

    public static void setImages(List<BufferedImage> images)
    {
        if (images == null) {
            pendingBufferedImages = null;
            return;
        }
        // make a copy to avoid accidental modification by caller
        pendingBufferedImages = new ArrayList<>(images);
    }

    @FXML
    public void initialize() {
        loadAllImages();


        //todo: some images zoom on the bottem right corner
        ImageView.setOnScroll((ScrollEvent se) -> {
            if (ImageView.getImage() == null) return;

            double delta = se.getDeltaY();
            double factor = Math.pow(1.0015, delta);

            double oldScale = scale;
            scale = clamp(scale * factor, 0.05, 10.0);

            // mouse position relative to ImageView
            double mouseX = se.getX();
            double mouseY = se.getY();

            // adjust translation so zoom happens around mouse
            translateX = mouseX - (mouseX - translateX) * (scale / oldScale);
            translateY = mouseY - (mouseY - translateY) * (scale / oldScale);

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
            // clear any previously loaded images (controller may be reused)
            images.clear();

            List<BufferedImage> bis = null;
            if (pendingBufferedImages != null) {
                bis = pendingBufferedImages;
            } else {
                bis = DataBase.getAllImages(false);
            }

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

            // clear pending after consuming so subsequent opens show all images again
            pendingBufferedImages = null;
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
}

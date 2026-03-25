package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class ViewIndividualImageController {
    @FXML
    private Button BackButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private Button FitButton;
    @FXML
    private Button ResetZoomButton;

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
        // install interaction handlers
        ImageView.setOnScroll((ScrollEvent se) -> {
            if (ImageView.getImage() == null) return;
            double delta = se.getDeltaY();
            double factor = Math.pow(1.0015, delta);

            double mx = se.getX();
            double my = se.getY();

            double imgX = (mx - translateX) / scale;
            double imgY = (my - translateY) / scale;

            scale = clamp(scale * factor, 0.05, 10.0);

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

    @FXML
    public void handleBack() {
        SceneManager.switchScene("View All Images.fxml");
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

        if (Double.isNaN(vw) || vw <= 0) vw = ImageView.getParent().getLayoutBounds().getWidth();
        if (Double.isNaN(vh) || vh <= 0) vh = ImageView.getParent().getLayoutBounds().getHeight();

        if (vw <= 0 || vh <= 0) return;

        double sx = vw / iw;
        double sy = vh / ih;
        scale = Math.max(0.01, Math.min(10.0, Math.min(sx, sy) * 0.95));

        translateX = (vw - iw * scale) / 2.0;
        translateY = (vh - ih * scale) / 2.0;
        updateTransforms();
    }

    @FXML
    public void handleResetZoom() {
        scale = 1.0;
        translateX = 0.0;
        translateY = 0.0;
        updateTransforms();
    }
}

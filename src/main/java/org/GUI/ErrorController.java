package org.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorController {

    @FXML
    private Label titleLabel;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button copyButton;

    public void handleClose() {
        // close the window
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCopy() {
        try {
            String text = messageArea.getText();
            if (text == null) return;
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            Clipboard.getSystemClipboard().setContent(content);
        } catch (Exception ignored) {
            // don't crash the app for clipboard failures
        }
    }

    /**
     * Show an error dialog with given title and message.
     */
    public static void showError(String title, String message) {
        showError(title, message, null);
    }

    /**
     * Show an error dialog with given title and message and optional throwable details appended.
     */
    public static void showError(String title, String message, Throwable t) {
        try {
            FXMLLoader loader = new FXMLLoader(ErrorController.class.getResource("/GUI/Error Box.fxml"));
            Parent root = loader.load();
            ErrorController controller = loader.getController();
            controller.titleLabel.setText(title);

            StringBuilder sb = new StringBuilder();
            if (message != null) sb.append(message);
            if (t != null) {
                sb.append("\n\nDetails:\n");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                t.printStackTrace(pw);
                sb.append(sw);
            }

            controller.messageArea.setText(sb.toString());

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle(title);
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convenience method to show informational popups that behave like errors for now.
     */
    public static void showInfo(String title, String message) {
        showError(title, message, null);
    }
}

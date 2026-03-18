package org.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login Menu.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Museum");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

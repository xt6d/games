package org.t6ddydv.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class DefaultSceneSwitcher {

    public static final SceneSwitcher INSTANCE = (source, fxml) -> {
        try {
            Parent root = FXMLLoader.load(DefaultSceneSwitcher.class.getResource(fxml));
            Scene scene = new Scene(root);
            scene.getStylesheets()
                    .add(DefaultSceneSwitcher.class.getResource("/gameAppStyle.css").toExternalForm());

            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };

    private DefaultSceneSwitcher() {}
}
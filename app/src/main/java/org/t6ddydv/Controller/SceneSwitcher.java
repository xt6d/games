package org.t6ddydv.Controller;

import javafx.scene.Node;

@FunctionalInterface
public interface SceneSwitcher {
    void change(Node source, String fxml);
}
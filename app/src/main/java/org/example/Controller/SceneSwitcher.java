package org.example.Controller;

import javafx.scene.Node;

@FunctionalInterface
public interface SceneSwitcher {
    void change(Node source, String fxml);
}
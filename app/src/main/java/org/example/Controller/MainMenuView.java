package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MainMenuView {
    private final SceneSwitcher sceneSwitch = DefaultSceneSwitcher.INSTANCE;
    @FXML private ImageView imageTicTacToe;
    @FXML private ImageView imageconnect4;
    @FXML private ImageView imagesudoku;


    @FXML
    private void initialize() {

        imageTicTacToe.setOnMouseClicked(e -> sceneSwitch.change(imageTicTacToe, "/ticTacToe.fxml"));
        imageconnect4.setOnMouseClicked(e -> sceneSwitch.change(imageconnect4, "/connect4.fxml"));
        imagesudoku.setOnMouseClicked(e -> sceneSwitch.change(imagesudoku, "/sudoku.fxml"));
    }

}

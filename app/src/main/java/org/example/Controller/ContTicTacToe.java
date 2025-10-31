package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.example.model.TicTacToeGame;

public class ContTicTacToe {

    @FXML private GridPane gameGrid;
    @FXML private Label statusLabel;
    @FXML private Button backButton;
    @FXML private Button resetButton;

    @FXML private Label playerXScoreLabel;
    @FXML private Label playerOScoreLabel;

    private final TicTacToeGame model;
    private boolean gameOver = false;

    private int scoreX = 0;
    private int scoreO = 0;

    private final Button[][] gridButtons = new Button[3][3];
    private final SceneSwitcher sceneSwitch = DefaultSceneSwitcher.INSTANCE;

    public ContTicTacToe() {
        this.model = new TicTacToeGame();
    }

    @FXML
    private void initialize() {
        // Popola la griglia con bottoni
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button button = new Button();
                // Usa la dimensione del GridPane (100x100)
                button.setPrefSize(100.0, 100.0);
                button.setFont(new Font(40));

                final int row = r;
                final int col = c;

                button.setOnAction(event -> handleGridClick(row, col));

                gridButtons[r][c] = button;
                gameGrid.add(button, c, r);
            }
        }
        updateUI();
        updateScoreLabels();
    }

    private void handleGridClick(int row, int col) {
        if (gameOver) return;

        boolean moved = model.makeMove(row, col);

        if (!moved) {
            statusLabel.setText("Mossa non valida!");
            return;
        }

        updateUI();

        TicTacToeGame.Result res = model.getResult();
        if (res != TicTacToeGame.Result.ONGOING) {
            gameOver = true;
            showResult(res);
        }
    }

    @FXML
    private void handleResetClick() {
        model.reset();
        gameOver = false;
        updateUI();
        statusLabel.setText(model.getCurrentPlayer() + " inizia");
    }

    @FXML
    private void handleBackClick() {
        sceneSwitch.change(backButton, "/scenesfxml/mainMenuView.fxml");
    }

    private void updateUI() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                String text = "";
                TicTacToeGame.Cell cell = model.getCell(r, c);
                if (cell == TicTacToeGame.Cell.X) {
                    text = "X";
                    gridButtons[r][c].setStyle("-fx-text-fill: blue;");
                } else if (cell == TicTacToeGame.Cell.O) {
                    text = "O";
                    gridButtons[r][c].setStyle("-fx-text-fill: red;");
                } else {
                    gridButtons[r][c].setStyle(null);
                }
                gridButtons[r][c].setText(text);
            }
        }
        statusLabel.setText("Turno di: " + model.getCurrentPlayer());
    }

    private void showResult(TicTacToeGame.Result result) {
        if (result == TicTacToeGame.Result.DRAW) {
            statusLabel.setText("Pareggio!");
        } else if (result == TicTacToeGame.Result.X_WINS) {
            statusLabel.setText("X Vince!");
            scoreX++;
        } else if (result == TicTacToeGame.Result.O_WINS) {
            statusLabel.setText("O Vince!");
            scoreO++;
        }
        updateScoreLabels();
    }

      private void updateScoreLabels() {
        playerXScoreLabel.setText("X: " + scoreX);
        playerOScoreLabel.setText("O: " + scoreO);
    }
}
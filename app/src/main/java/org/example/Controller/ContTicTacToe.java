package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.example.model.TicTacToeGame;

public class ContTicTacToe {

    // 1. Collega i componenti FXML
    @FXML private GridPane gameGrid;
    @FXML private Label statusLabel;
    @FXML private Button backButton;
    @FXML private Button resetButton;

    // 2. Il controller crea e possiede il modello
    private final TicTacToeGame model;
    private boolean gameOver = false;

    // Array per tenere traccia dei bottoni della griglia
    private final Button[][] gridButtons = new Button[3][3];

    // Riferimento allo SceneSwitcher
    private final SceneSwitcher sceneSwitch = DefaultSceneSwitcher.INSTANCE;

    // 3. Costruttore senza argomenti (richiesto da FXML)
    public ContTicTacToe() {
        this.model = new TicTacToeGame();
    }

    // 4. Metodo Initialize (chiamato da JavaFX dopo il caricamento)
    @FXML
    private void initialize() {
        // Popola la griglia con bottoni cliccabili
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button button = new Button();
                button.setPrefSize(100.0, 100.0); // Imposta dimensione
                button.setFont(new Font(40));     // Imposta font grande

                final int row = r;
                final int col = c;

                // Aggiungi l'azione di click
                button.setOnAction(event -> handleGridClick(row, col));

                gridButtons[r][c] = button;
                gameGrid.add(button, c, r); // Aggiunge alla griglia (colonna, riga)
            }
        }
        updateUI(); // Aggiorna la UI allo stato iniziale
    }

    // 5. Gestore per i click sulla griglia
    private void handleGridClick(int row, int col) {
        if (gameOver) return; // Non fare nulla se il gioco è finito

        // Tenta di fare la mossa sul modello
        boolean moved = model.makeMove(row, col);

        if (!moved) {
            statusLabel.setText("Mossa non valida!");
            return;
        }

        // Aggiorna la UI dopo la mossa
        updateUI();

        // Controlla il risultato
        TicTacToeGame.Result res = model.getResult();
        if (res != TicTacToeGame.Result.ONGOING) {
            gameOver = true;
            showResult(res);
        }
    }

    // 6. Gestori per i bottoni Reset e Back
    @FXML
    private void handleResetClick() {
        model.reset();
        gameOver = false;
        updateUI(); // Pulisce la griglia e aggiorna le etichette
        statusLabel.setText(model.getCurrentPlayer() + " inizia");
    }

    @FXML
    private void handleBackClick() {
        // Usa lo SceneSwitcher che hai già
        sceneSwitch.change(backButton, "/scenesfxml/mainMenuView.fxml");
    }

    // 7. Metodi helper per aggiornare la UI
    private void updateUI() {
        // Aggiorna ogni bottone della griglia
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
                    gridButtons[r][c].setStyle(null); // Resetta lo stile
                }
                gridButtons[r][c].setText(text);
            }
        }
        // Aggiorna l'etichetta del turno
        statusLabel.setText("Turno di: " + model.getCurrentPlayer());
    }

    private void showResult(TicTacToeGame.Result result) {
        if (result == TicTacToeGame.Result.DRAW) {
            statusLabel.setText("Pareggio!");
        } else if (result == TicTacToeGame.Result.X_WINS) {
            statusLabel.setText("X Vince!");
        } else if (result == TicTacToeGame.Result.O_WINS) {
            statusLabel.setText("O Vince!");
        }
    }
}
package org.t6ddydv.model;

public class TicTacToeGame {
    public enum Cell { EMPTY, X, O }
    public enum Result { X_WINS, O_WINS, DRAW, ONGOING }

    private final Cell[][] board = new Cell[3][3];
    private Cell current = Cell.X;

    public TicTacToeGame() { reset(); }

    public void reset() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                board[r][c] = Cell.EMPTY;
        current = Cell.X;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) return false;
        if (board[row][col] != Cell.EMPTY) return false;
        board[row][col] = current;
        current = (current == Cell.X) ? Cell.O : Cell.X;
        return true;
    }

    public Cell getCell(int row, int col) { return board[row][col]; }
    public Cell getCurrentPlayer() { return current; }

    public Result getResult() {
        // righe/colonne/diagonali
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != Cell.EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0] == Cell.X ? Result.X_WINS : Result.O_WINS;
            if (board[0][i] != Cell.EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i] == Cell.X ? Result.X_WINS : Result.O_WINS;
        }
        if (board[0][0] != Cell.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0] == Cell.X ? Result.X_WINS : Result.O_WINS;
        if (board[0][2] != Cell.EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2] == Cell.X ? Result.X_WINS : Result.O_WINS;

        // controllo pareggio
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == Cell.EMPTY) return Result.ONGOING;
        return Result.DRAW;
    }
}
package org.example.Controller;

import org.example.model.TicTacToeGame;
public class ContTicTacToe {
    private final TicTacToeGame model;
    private boolean gameOver = false;

    // Callback interfaces
    public interface UpdateCellListener { void onUpdate(int row, int col, TicTacToeGame.Cell cell); }
    public interface ResultListener { void onResult(TicTacToeGame.Result result); }
    public interface InvalidMoveListener { void onInvalidMove(int row, int col); }
    public interface ResetListener { void onReset(); }

    private UpdateCellListener updateCellListener;
    private ResultListener resultListener;
    private InvalidMoveListener invalidMoveListener;
    private ResetListener resetListener;

    public ContTicTacToe(TicTacToeGame model) {
        this.model = model;
    }

    // Listener setters
    public void setUpdateCellListener(UpdateCellListener l) { this.updateCellListener = l; }
    public void setResultListener(ResultListener l) { this.resultListener = l; }
    public void setInvalidMoveListener(InvalidMoveListener l) { this.invalidMoveListener = l; }
    public void setResetListener(ResetListener l) { this.resetListener = l; }

    public void click(int row, int col) {
        if (gameOver) return;

        if (!validIndex(row) || !validIndex(col)) {
            if (invalidMoveListener != null) invalidMoveListener.onInvalidMove(row, col);
            return;
        }

        boolean moved = model.makeMove(row, col);
        if (!moved) {
            if (invalidMoveListener != null) invalidMoveListener.onInvalidMove(row, col);
            return;
        }

        if (updateCellListener != null) updateCellListener.onUpdate(row, col, model.getCell(row, col));

        TicTacToeGame.Result res = model.getResult();
        if (res != TicTacToeGame.Result.ONGOING) {
            gameOver = true;
            if (resultListener != null) resultListener.onResult(res);
        }
    }

    // Reset model and notify UI to refresh
    public void reset() {
        model.reset();
        gameOver = false;
        if (resetListener != null) resetListener.onReset();
        // refresh full board so GUI can repaint each cell
        if (updateCellListener != null) {
            for (int r = 0; r < 3; r++)
                for (int c = 0; c < 3; c++)
                    updateCellListener.onUpdate(r, c, model.getCell(r, c));
        }
    }

    public TicTacToeGame.Cell getCell(int row, int col) {
        if (!validIndex(row) || !validIndex(col)) throw new IndexOutOfBoundsException("0..2");
        return model.getCell(row, col);
    }
    public TicTacToeGame.Cell getCurrentPlayer() { return model.getCurrentPlayer(); }
    public TicTacToeGame.Result getResult() { return model.getResult(); }

    private boolean validIndex(int i) { return i >= 0 && i <= 2; }
}

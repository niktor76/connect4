package game;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class FXView implements BoardView, InputDevice{
    AnchorPane rootElement;
    GridPane gameBoard;
    BoardModel boardModel;
    ArrayList<InputListener> inputListeners;

    @Override
    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public FXView(AnchorPane rootElement) {
        this.rootElement = rootElement;
        this.init();
    }

    private void init() {
        this.gameBoard = (GridPane) rootElement.lookup("#board4gewinnt");
    }

    void notifyInputListeners(int columnClicked){
        inputListeners.forEach(e -> e.columnClicked(columnClicked));
    }

    @Override
    public void refreshBoardView() {
        gameBoard.getChildren().removeAll();
        int[][] board = boardModel.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Color color;
                switch (board[row][col]) {
                    case 0:
                        color = Color.WHITE;
                        break;
                    case 1:
                        color = Color.YELLOW;
                        break;
                    case 2:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.BLACK;
                }

                Circle circle = new Circle(38, color);
                int x = col * 100 + 50;
                int y = row * 100 + 50;
                gameBoard.add(circle, col, row);
                GridPane.setMargin(circle, new Insets(0, 0,0,12));
            }
        }
    }

    @Override
    public void addListener(InputListener inputListener) {
        inputListeners.add(inputListener);
    }
}

package game;

import java.util.ArrayList;

public class BoardModel {
    private int[][] board; // values: 0 = empty, 1 = player1, 2 = player2
    private BoardView boardView;
    private Game game;

    public BoardModel(BoardView boardView, Game game) {
        this.board  = new int[6][7];
        this.boardView = boardView;
        this.game = game;
        this.init();
    }

    private void init() {

    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int evaluateSituation(Player player) {
        int playerNumber = player.getNumber();
        return 1;
    }

    private int count4connected (Player player) {
        return 1;
    }

    public ArrayList<Integer> getFreeColumns() {
        ArrayList<Integer> freeColumns = new ArrayList<>();
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == 0) {
                freeColumns.add(col);
            }
        }
        return freeColumns;
    }

    public boolean isFreeColumn(int columnIndex) {
        return getFreeColumns().contains(columnIndex);
    }

    public void makeTurnOnColumn(int columnIndex, int playerNumber) {
        System.out.println(columnIndex);
        for (int row = 5; row >= 0 ; row--) {
            if (board[row][columnIndex] == 0) {
                board[row][columnIndex] = (byte) playerNumber;
                boardView.refreshBoardView();
                break;
            }
        }
        // check if game finished
        game.changePlayer();
    }

    public void restartGame() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = 0;
            }
        }
        boardView.refreshBoardView();
    }

    public void printBoard() {
        System.out.println("board");
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}

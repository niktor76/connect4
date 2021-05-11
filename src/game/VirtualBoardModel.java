package game;

import java.util.ArrayList;

public class VirtualBoardModel {
    private int[][] board; // values: 0 = empty, 1 = player1, 2 = player2

    public VirtualBoardModel(int[][] board) {
        this.board  = board;
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
        for (int row = 5; row >= 0 ; row--) {
            if (board[row][columnIndex] == 0) {
                board[row][columnIndex] = (byte) playerNumber;
                break;
            }
        }
    }

    public void restartGame() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = 0;
            }
        }
    }

    public int getRating(int playerNumber) {
        int ratingSum = 0;
        boolean [][][] isCheckedVektor = new boolean[6][7][4]; // 3. Dimension: 1 = right, 2 = right/down, 3 = down, 4 = left/down
        int height = board.length;
        int width = board[0].length;

        ArrayList<Vektor> vektors = new ArrayList<>();
        vektors.add(new Vektor(Vektor.RIGHT, 1, 0));
        vektors.add(new Vektor(Vektor.RIGHT_DOWN, 1, 1));
        vektors.add(new Vektor(Vektor.DOWN, 0, 1));
        vektors.add(new Vektor(Vektor.LEFT_DOWN, -1, 1));

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] != 0) {
                    for (Vektor vektor : vektors) {
                        if (!isCheckedVektor[row][col][vektor.direction]) {
                            isCheckedVektor[row][col][vektor.direction] = true;
                            int tokenCounter = 1;
                            boolean isContinuing = true;
                            while (isInsideBoard(row, col, tokenCounter, vektor) && isContinuing) {
                                if (getPlayerNumber(row, col, tokenCounter, vektor) == playerNumber) {
                                    tokenCounter++;
                                    isContinuing = true;
                                    isCheckedVektor[row][col][vektor.direction] = true;
                                } else {
                                    isContinuing = false;
                                }
                            }

                            int spaceCounter = tokenCounter;
                            int counter = 1;
                            while (spaceCounter < 4
                                    && isInsideBoard(row, col, counter, vektor.getOppositeVektorInstance())
                                    && getPlayerNumber(row, col, counter, vektor.getOppositeVektorInstance()) == 0) {
                                counter++;
                                spaceCounter++;
                            }

                            counter = 1;
                            while (spaceCounter < 4
                                    && isInsideBoard(row, col, counter + tokenCounter, vektor)
                                    && getPlayerNumber(row, col, counter +tokenCounter, vektor) == 0) {
                                counter++;
                                spaceCounter++;
                            }

                            if (spaceCounter > 3) {
                                if (board[row][col] == playerNumber) {
                                    ratingSum += getValueForLength(tokenCounter);
                                } else {
                                    ratingSum -= getValueForLength(tokenCounter) * 5;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ratingSum;
    }

    public void printBoard() {
        System.out.println("virtual board");
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isInsideBoard(int row, int col, int counter, Vektor vektor) {
        int calculatedCol = col + (counter * vektor.y);
        boolean isInsideBoardX = calculatedCol >= 0 && calculatedCol <= 6;
        int calculatedRow = row + (counter * vektor.x);
        boolean isInsideBoardY = calculatedRow >= 0 && calculatedRow <= 5;
        return isInsideBoardX && isInsideBoardY;
    }

    private int getPlayerNumber(int row, int col, int counter, Vektor vektor) {
        if (!isInsideBoard(row, col, counter, vektor)) {
            return -1;
        }
        int calculatedCol = col + (counter * vektor.y);
        int calculatedRow = row + (counter * vektor.x);
        return board[calculatedRow][calculatedCol];
    }

    private int getValueForLength(int length) {
        switch (length) {
            case 1:
                return 1;
            case 2:
                return 10;
            case 3:
                return 100;
            case 4:
                return 1000000;
        }
        return -1;
    }
}

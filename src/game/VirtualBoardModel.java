package game;

import java.util.ArrayList;

public class VirtualBoardModel {
    private int[][] board; // values: 0 = empty, 1 = player1, 2 = player2

    public VirtualBoardModel(int[][] board) {
        this.board  = new int[6][7];
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
        System.out.println(freeColumns);
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
        boolean [][][] vektors = new boolean[6][7][4]; // 3. Dimension: 1 = right, 2 = right/down, 3 = down, 4 = left/down
        final int RIGHT = 1;
        final int RIGHT_DOWN = 2;
        final int DOWN = 3;
        final int LEFT_DOWN = 4;
        int height = board.length;
        int width = board[0].length;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] != 0) {
                    // look in the right direction
                    if (!vektors[row][col][RIGHT]) {
                        vektors[row][col][RIGHT] = true;
                        int tokenCounter = 1;
                        int freeSpaceCounter = 0;
                        boolean isContinuing = true;
                        while ((col + tokenCounter < width) && isContinuing) {
                            if (board[row][col + tokenCounter] == playerNumber) {
                                vektors[row][col][RIGHT] = true;
                                tokenCounter++;
                                isContinuing = true;
                            } else {
                                isContinuing = false;
                            }
                        }

                        int spaceCounter = tokenCounter;
                        int counter = 1;
                        while (spaceCounter < 4 && (col - counter) >= 0 && board[row][col - counter] == 0) {
                            counter++;
                            spaceCounter++;
                        }

                        counter = 1;
                        while (spaceCounter < 4 && (col + tokenCounter + counter) < width && board[row][col + tokenCounter + counter] == 0) {
                            counter++;
                            spaceCounter++;
                        }

                        if (spaceCounter > 3) {
                            if (board[row][col] == playerNumber) {
                                ratingSum += getValueForLength(tokenCounter);
                            } else {
                                ratingSum -= getValueForLength(tokenCounter);
                            }
                        }
                    }

                    // look right/down
                    if (!vektors[row][col][RIGHT_DOWN]) {
                        vektors[row][col][RIGHT_DOWN] = true;
                        int counter = 1;
                        boolean isContinuing = true;
                        while ((col + counter < width && row + counter < height) && isContinuing) {
                            if (board[row + counter][col + counter] == playerNumber) {
                                vektors[row][col][RIGHT_DOWN] = true;
                                counter++;
                                isContinuing = true;
                            } else {
                                isContinuing = false;
                            }
                        }

                        if (board[row][col] == playerNumber) {
                            ratingSum += getValueForLength(counter);
                        } else {
                            ratingSum -= getValueForLength(counter);
                        }
                    }

                    // look down
                    if (!vektors[row][col][DOWN]) {
                        vektors[row][col][DOWN] = true;
                        int counter = 1;
                        boolean isContinuing = true;
                        while ((row + counter < height) && isContinuing) {
                            if (board[row + counter][col] == playerNumber) {
                                vektors[row][col][DOWN] = true;
                                counter++;
                                isContinuing = true;
                            } else {
                                isContinuing = false;
                            }
                        }

                        if (board[row][col] == playerNumber) {
                            ratingSum += getValueForLength(counter);
                        } else {
                            ratingSum -= getValueForLength(counter);
                        }
                    }

                    // look left/down
                    if (!vektors[row][col][LEFT_DOWN]) {
                        vektors[row][col][LEFT_DOWN] = true;
                        int counter = 1;
                        boolean isContinuing = true;
                        while ((col - counter >= 0 && row + counter < height) && isContinuing) {
                            if (board[row + counter][col - counter] == playerNumber) {
                                vektors[row][col][LEFT_DOWN] = true;
                                counter++;
                                isContinuing = true;
                            } else {
                                isContinuing = false;
                            }
                        }

                        if (board[row][col] == playerNumber) {
                            ratingSum += getValueForLength(counter);
                        } else {
                            ratingSum -= getValueForLength(counter);
                        }
                    }
                }
            }
        }
        return ratingSum;
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

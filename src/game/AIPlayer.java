package game;

import java.util.ArrayList;
import java.util.Arrays;

public class AIPlayer implements Player {

    private final BoardModel boardModel;
    private int playerNumber;
    private int maxDepth;

    public AIPlayer(BoardModel boardModel, int playerNumber, int maxDepth) {
        this.boardModel = boardModel;
        this.playerNumber = playerNumber;
        this.maxDepth = maxDepth;
    }

    @Override
    public void setNumber(int Number) {

    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public void columnClicked(int columnIndex) {
        boardModel.makeTurnOnColumn(columnIndex, playerNumber);
    }

    @Override
    public void makeTurn() {
        MiniMaxResult miniMaxResult = new MiniMaxResult(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (Integer columnIndex : boardModel.getFreeColumns()) {
            VirtualBoardModel virtualBoardModel = new VirtualBoardModel(Arrays.stream(boardModel.getBoard()).map(int[]::clone).toArray(int[][]::new));
            virtualBoardModel.makeTurnOnColumn(columnIndex, 1);

            int ratingOfColumn = recursiveMiniMax(virtualBoardModel, playerNumber, 1);
            System.out.println(columnIndex + ": " + ratingOfColumn);
            if (ratingOfColumn > miniMaxResult.ratingOfColumn) {
                miniMaxResult.ratingOfColumn = ratingOfColumn;
                miniMaxResult.columnIndex = columnIndex;
            }
        }
        System.out.println("column of choice: " + miniMaxResult.columnIndex);
        this.columnClicked(miniMaxResult.columnIndex);
    }

    private int recursiveMiniMax(VirtualBoardModel virtualBoardModel, int currentVirtualPlayerNumber, int depthCounter) {
        int bestRating;
        if (currentVirtualPlayerNumber == this.playerNumber) {
            bestRating = Integer.MIN_VALUE;
        } else {
             bestRating = Integer.MAX_VALUE;
        }
        int nextVirtualPlayerNumber = currentVirtualPlayerNumber == 1 ? 2 : 1;
        for (Integer column : virtualBoardModel.getFreeColumns()) {
            int[][] virtualBoard = virtualBoardModel.getBoard();
            VirtualBoardModel virtualBoardModelNextMove = new VirtualBoardModel(Arrays.stream(virtualBoard).map(int[]::clone).toArray(int[][]::new));
            virtualBoardModelNextMove.makeTurnOnColumn(column, currentVirtualPlayerNumber);
            if (depthCounter < maxDepth) {
                int rating = recursiveMiniMax(virtualBoardModelNextMove, nextVirtualPlayerNumber, ++depthCounter);
                if (currentVirtualPlayerNumber == this.playerNumber) {
                    bestRating = Math.max(bestRating, rating);
                } else {
                    bestRating = Math.min(bestRating, rating);
                }
            } else {
                return virtualBoardModel.getRating(currentVirtualPlayerNumber);
            }
        }
        return bestRating;
    }
//    @Override
//    public void makeTurn() {
//        int[][] board = boardModel.getBoard();
//        VirtualBoardModel virtualBoardModel = new VirtualBoardModel(Arrays.stream(board).map(int[]::clone).toArray(int[][]::new));
//        int columnOfChoice = recursiveMiniMax(virtualBoardModel, playerNumber, 1).columnIndex;
//        this.columnClicked(columnOfChoice);
//    }
//
//    private MiniMaxResult recursiveMiniMax(VirtualBoardModel virtualBoardModel, int currentVirtualPlayerNumber, int depthCounter) {
//        ArrayList<MiniMaxResult> miniMaxResults = new ArrayList<>();
//        MiniMaxResult bestResult = new MiniMaxResult(0, 0);
//        int bestRating = 0;
//        int nextVirtualPlayerNumber = currentVirtualPlayerNumber == 1 ? 2 : 1;
//        for (Integer column : virtualBoardModel.getFreeColumns()) {
//            int[][] virtualBoard = virtualBoardModel.getBoard();
//            VirtualBoardModel virtualBoardModelNextMove = new VirtualBoardModel(Arrays.stream(virtualBoard).map(int[]::clone).toArray(int[][]::new));
//            virtualBoardModelNextMove.makeTurnOnColumn(column, currentVirtualPlayerNumber);
//            if (depthCounter < maxDepth) {
//                MiniMaxResult result = recursiveMiniMax(virtualBoardModelNextMove, nextVirtualPlayerNumber, ++depthCounter);
//                miniMaxResults.add(new MiniMaxResult(result.ratingOfColumn, column));
//                if (currentVirtualPlayerNumber == this.playerNumber) {
//                    if (result.ratingOfColumn > bestRating) {
//                        bestRating = result.ratingOfColumn;
//                        bestResult = new MiniMaxResult(bestRating, column);
//                    }
//                } else {
//                    if (result.ratingOfColumn < bestRating) {
//                        bestRating = result.ratingOfColumn;
//                        bestResult = new MiniMaxResult(bestRating, column);
//                    }
//
//                }
//            } else {
//                int rating = virtualBoardModel.getRating(currentVirtualPlayerNumber);
//                bestResult = new MiniMaxResult(rating, column);
//            }
//        }
//        miniMaxResults.forEach(miniMaxResult -> {
//            System.out.println(miniMaxResult.columnIndex + " " + miniMaxResult.ratingOfColumn);
//        });
//        System.out.println("bestResult: " + bestResult.columnIndex + " : " + bestResult.ratingOfColumn);
//        return bestResult;
//    }
}

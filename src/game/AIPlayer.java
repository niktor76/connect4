package game;

public class AIPlayer implements Player{

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
        VirtualBoardModel virtualBoardModel = new VirtualBoardModel(boardModel.getBoard());
        int columnOfChoice = recursiveMiniMax(virtualBoardModel, playerNumber, 1).columnIndex;
        this.columnClicked(columnOfChoice);
    }

    private MiniMaxResult recursiveMiniMax(VirtualBoardModel virtualBoardModel, int playerNumber, int depthCounter) {
        MiniMaxResult bestResult = new MiniMaxResult(0,0);
        for (Integer column: virtualBoardModel.getFreeColumns()){
            VirtualBoardModel virtualBoardModelNextMove = new VirtualBoardModel(virtualBoardModel.getBoard());
            virtualBoardModelNextMove.makeTurnOnColumn(column, playerNumber);
            System.out.println(depthCounter);
            if (depthCounter < maxDepth) {
                int switchedPlayerNumber = playerNumber == 1 ? 2 : 1;
                MiniMaxResult result = recursiveMiniMax(virtualBoardModelNextMove, switchedPlayerNumber, ++depthCounter);
                bestResult = result.ratingOfColumn > bestResult.ratingOfColumn ? result : bestResult;
            } else {
                int rating = virtualBoardModel.getRating(playerNumber);
                System.out.println(rating);
                bestResult = new MiniMaxResult(rating, column);
            }
        }
        return bestResult;
    }
}

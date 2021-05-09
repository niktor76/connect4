package game;

public class AIPlayer implements Player{

    private final BoardModel boardModel;
    private int playerNumber;
    private int intelligence;

    public AIPlayer(BoardModel boardModel, int playerNumber, int intelligence) {
        this.boardModel = boardModel;
        this.playerNumber = playerNumber;
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
        int[][] virtualBoard = virtualBoardModel.getBoard();
        recursiveMiniMax(virtualBoardModel, playerNumber, 1);
    }

    private MiniMaxResult recursiveMiniMax(VirtualBoardModel virtualBoardModel, int playerNumber, int counter) {
        MiniMaxResult bestResult = new MiniMaxResult(0,0);
        for (Integer column: virtualBoardModel.getFreeColumns()){
            VirtualBoardModel virtualBoardModelNextMove = new VirtualBoardModel(virtualBoardModel.getBoard());
            virtualBoardModelNextMove.makeTurnOnColumn(column, playerNumber);

            if (counter < intelligence) {
                int switchedPlayerNumber = playerNumber == 1 ? 2 : 1;
                MiniMaxResult result = recursiveMiniMax(virtualBoardModelNextMove, switchedPlayerNumber, counter++);
                bestResult = result.ratingOfColumn > bestResult.ratingOfColumn ? result : bestResult;
            } else {
                int rating = virtualBoardModel.getRating(playerNumber);
                bestResult = new MiniMaxResult(rating, column);
            }
        }
        return bestResult;
    }
}

package game;

public class HumanPlayer implements Player {
    private final BoardModel boardModel;
    private int playerNumber;
    private String name;
    private int number;

    public HumanPlayer(BoardModel boardModel, int playerNumber) {
        this.boardModel = boardModel;
        this.playerNumber = playerNumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }


    @Override
    public void columnClicked(int columnIndex) {
        if (boardModel.isFreeColumn(columnIndex)) {
            boardModel.makeTurnOnColumn(columnIndex, playerNumber);
        }
    }

    @Override
    public void makeTurn() {
    }
}

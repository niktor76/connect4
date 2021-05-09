package game;

public class Game implements InputListener{
    private final Controller controller;
    private BoardModel boardModel;
    private Player player1, player2;
    private BoardView boardView;
    private InputDevice fxViewInput;
    private Player currentPlayer;

    public Game(Controller controller, BoardView boardView)  {
        this.controller = controller;
        this.boardView = boardView;
        this.init();
    }

    private void init() {
        this.controller.setGame(this);
        boardModel = new BoardModel(boardView, this);
        player1 = new HumanPlayer(boardModel, 1);
        player2 = new AIPlayer(boardModel, 2, 3);
        boardView.setBoardModel(boardModel);
        currentPlayer = player1;
        boardView.refreshBoardView();
    }

    @Override
    public void columnClicked(int columnIndex) {
        this.currentPlayer.columnClicked(columnIndex);
    }

    public void restartGame() {
        boardModel.restartGame();
    }

    public void changePlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        if (currentPlayer instanceof AIPlayer) {
            currentPlayer.makeTurn();
        }
    }
}

package game;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
    Game game;

    public void boardMouseClicked(MouseEvent mouseEvent) {
        int columnClicked = (int) Math.floor(mouseEvent.getX() / 100);
        game.columnClicked(columnClicked);
    }

    public void boardKeyTyped(KeyEvent keyEvent) {

    }

    public void restartButtonClicked(ActionEvent actionEvent) {
        this.game.restartGame();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

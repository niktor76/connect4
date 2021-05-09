package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
        AnchorPane rootElement = loader.load();
        Controller controller = loader.getController();
        BoardView boardView = new FXView(rootElement);
        Game game = new Game(controller, boardView);

        Scene scene = new Scene(rootElement, 800, 800);
        primaryStage.setTitle("4 Gewinnt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package boardview;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import gamecontrol.AIChessController;
import gamecontrol.ChessController;
import gamecontrol.GameController;
import gamecontrol.NetworkedChessController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
/**
 * Main class for the chess application
 * Sets up the top level of the GUI
 * @author Gustavo
 * @version
 */
public class ChessFX extends Application {

    private GameController controller;
    private BoardView board;
    private Text state;
    private Text sideStatus;
    private VBox root;

    @Override
    public void start(Stage primaryStage) {
        Button resetButton = new Button("Reset");
        Button aiGameButton = new Button("Play against your PC");
        TextField ipText = new TextField("");
        Button hostButton = new Button("Host");
        Button joinButton = new Button("Join");
        Text myIp = new Text("");
        try {
            myIp.setText(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            myIp.setText("Network Error!");
        }
        controller = new ChessController();
        state = new Text((controller.getCurrentState().toString()));
        sideStatus = new Text(controller.getCurrentSide().toString());
        board = new BoardView(controller, state, sideStatus);
        Pane paneOne = board.getView();


        resetButton.setOnAction(e -> {
                board.reset(new ChessController());
            });

        aiGameButton.setOnAction(e -> {
                board.reset(new AIChessController());
            });
        hostButton.setOnMouseClicked(makeHostListener());
        state.setText("New Game");
        joinButton.setOnMouseClicked(
            makeJoinListener(ipText));
        state.setText("New Game");
        joinButton.disableProperty()
            .bind(Bindings.isEmpty(ipText.textProperty()));

        VBox game = new VBox(5);
        HBox statusBar = new HBox(200);
        HBox hostControl = new HBox(100);
        HBox joinControl = new HBox(10);
        HBox resetButtons = new HBox(10);
        HBox firstBar = new HBox(10);
        HBox secondBar = new HBox(10);

        resetButtons.getChildren().addAll(resetButton, aiGameButton);
        statusBar.getChildren().addAll(state, sideStatus);
        hostControl.getChildren().addAll(hostButton, myIp);
        joinControl.getChildren().addAll(ipText, joinButton);
        firstBar.getChildren().addAll(resetButtons, statusBar);
        secondBar.getChildren().addAll(hostControl, joinControl);
        game.getChildren().addAll(paneOne, firstBar, secondBar);


        Scene scene = new Scene(game, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Game - Nathalie V 1.0");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private EventHandler<? super MouseEvent> makeHostListener() {
        return event -> {
            board.reset(new NetworkedChessController());
        };
    }

    private EventHandler<? super MouseEvent> makeJoinListener(TextField input) {
        return event -> {
            try {
                InetAddress addr = InetAddress.getByName(input.getText());
                GameController newController
                    = new NetworkedChessController(addr);
                board.reset(newController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
    }
}

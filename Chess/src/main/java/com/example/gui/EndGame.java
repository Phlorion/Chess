package com.example.gui;

import com.example.Game;
import com.example.chess.board.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public abstract class EndGame {

    String s;
    Button newGame;
    Button quit;

    public EndGame(Board board) {
        Stage stage = new Stage();
        stage.setResizable(false);
        VBox layout = new VBox();
        layout.setSpacing(16.f);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 100);
        setDisplayMessage(board);
        Label l = new Label();
        l.setText(s);
        layout.getChildren().add(l);

        HBox buttonsHolder = new HBox();
        buttonsHolder.setAlignment(Pos.CENTER);
        buttonsHolder.setSpacing(16.f);
        layout.getChildren().add(buttonsHolder);

        newGame = new Button();
        newGame.setText("New Game");
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        buttonsHolder.getChildren().add(newGame);

        quit = new Button();
        quit.setText("Quit");
        buttonsHolder.getChildren().add(quit);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Display a message with the end game info about the game
     */
    abstract void setDisplayMessage(Board board);


    static class CheckMate extends EndGame {
        public CheckMate(Board board) {
            super(board);
        }

        @Override
        void setDisplayMessage(Board board) {
            s = "" + board.getOpponentPlayer() + " player wins.";
        }
    }


    static class StaleMate extends EndGame {
        public StaleMate(Board board) {
            super(board);
        }

        @Override
        void setDisplayMessage(Board board) {
            s = "Tie.";
        }
    }
}

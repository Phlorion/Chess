package com.example.scrap;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class CircleTest extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Circle Test");
        stage.setResizable(false);
        GridPane layout = new GridPane();

        Scene scene = new Scene(layout, 256, 256);

        ColumnConstraints base = new ColumnConstraints();
        base.setPercentWidth(50);
        layout.getColumnConstraints().add(base);

        // Empty tile circle 1
        Pane tile1 = new Pane();
        tile1.setPrefSize(128, 128);
        tile1.setStyle("-fx-background-color: #eeeed2;");
        GridPane.setConstraints(tile1, 0, 0);
        layout.getChildren().add(tile1);

        Circle circle = new Circle();
        circle.setCenterX(64);
        circle.setCenterY(64);
        circle.setRadius(32.f);
        circle.setOpacity(0.5f);
        tile1.getChildren().add(circle);

        Label l1 = new Label();
        l1.setText("8");
        l1.setPrefWidth(tile1.getPrefWidth());
        l1.setPrefHeight(tile1.getPrefHeight());
        l1.setFont(Font.font("Arial", 24));
        l1.setAlignment(Pos.BOTTOM_RIGHT);
        tile1.getChildren().add(l1);

        // Empty tile circle 2
        Pane tile2 = new Pane();
        tile2.setPrefSize(128, 128);
        tile2.setStyle("-fx-background-color: #769655;");
        GridPane.setConstraints(tile2, 1, 0);
        layout.getChildren().add(tile2);

        Circle circle2 = new Circle();
        circle2.setCenterX(64);
        circle2.setCenterY(64);
        circle2.setRadius(32.f);
        circle2.setOpacity(0.5f);
        tile2.getChildren().add(circle2);

        // Occupied tile circle 1
        Pane occTile1 = new Pane();
        occTile1.setPrefSize(128, 128);
        occTile1.setStyle("-fx-background-color: #769655;");
        GridPane.setConstraints(occTile1, 0, 1);
        ImageView piece1 = new ImageView(new Image(new FileInputStream("pieces/Chess_plt60.png")));
        layout.getChildren().add(occTile1);

        occTile1.getChildren().add(piece1);

        Circle doughnut = new Circle();
        doughnut.setCenterX(64);
        doughnut.setCenterY(64);
        doughnut.setRadius(56.f);
        doughnut.setOpacity(0.5f);
        occTile1.getChildren().add(0, doughnut);
        Circle inside = new Circle();
        inside.setCenterX(64);
        inside.setCenterY(64);
        inside.setRadius(40.f);
        inside.setStyle("-fx-fill: #769655");
        occTile1.getChildren().add(1, inside);

        // Occupied tile circle 2
        Pane occTile2 = new Pane();
        occTile2.setPrefSize(128, 128);
        occTile2.setStyle("-fx-background-color: #eeeed2;");
        GridPane.setConstraints(occTile2, 1, 1);
        ImageView piece2 = new ImageView(new Image(new FileInputStream("pieces/Chess_pdt60.png")));
        layout.getChildren().add(occTile2);

        occTile2.getChildren().add(piece2);

        Circle doughnut2 = new Circle();
        doughnut2.setCenterX(64);
        doughnut2.setCenterY(64);
        doughnut2.setRadius(56.f);
        doughnut2.setOpacity(0.5f);
        occTile2.getChildren().add(0, doughnut2);
        Circle inside2 = new Circle();
        inside2.setCenterX(64);
        inside2.setCenterY(64);
        inside2.setRadius(40.f);
        inside2.setStyle("-fx-fill: #eeeed2");
        occTile2.getChildren().add(1, inside2);

        stage.setScene(scene);
        stage.show();
    }
}

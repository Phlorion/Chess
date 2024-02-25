package com.example.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class MoveHistory {

    GridPane layout;
    int lastWhiteLabelI;
    int lastBlackLabelI;

    public MoveHistory(GridPane root, int width) {
        layout = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setPrefWidth(Table.MOVES_HISTORY_WIDTH);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        GridPane.setConstraints(scrollPane, 1, 0);
        root.getChildren().add(scrollPane);
        ColumnConstraints moveHistoryCollConstraints = new ColumnConstraints();
        moveHistoryCollConstraints.setPercentWidth(width);
        layout.getColumnConstraints().add(moveHistoryCollConstraints);

        Label movesWhiteLabel = new Label();
        movesWhiteLabel.setText("White");
        movesWhiteLabel.setPrefWidth(45);
        GridPane.setConstraints(movesWhiteLabel, 0, 0);
        layout.getChildren().add(movesWhiteLabel);

        Label movesBlackLabel = new Label();
        movesBlackLabel.setText("Black");
        movesBlackLabel.setPrefWidth(45);
        GridPane.setConstraints(movesBlackLabel, 1, 0);
        layout.getChildren().add(movesBlackLabel);

        lastWhiteLabelI = 1; lastBlackLabelI = 1;
    }

    public GridPane getLayout() {
        return layout;
    }

    public void setLayout(GridPane layout) {
        this.layout = layout;
    }

    public int getLastWhiteLabelI() {
        return lastWhiteLabelI;
    }

    public void setLastWhiteLabelI(int lastWhiteLabelI) {
        this.lastWhiteLabelI = lastWhiteLabelI;
    }

    public int getLastBlackLabelI() {
        return lastBlackLabelI;
    }

    public void setLastBlackLabelI(int lastBlackLabelI) {
        this.lastBlackLabelI = lastBlackLabelI;
    }

    public void add(Node node, int column) {
        int columnI;
        if (column == 0) {
            columnI = lastWhiteLabelI;
            lastWhiteLabelI++;
        }
        else {
            columnI = lastBlackLabelI;
            lastBlackLabelI++;
        }
        GridPane.setConstraints(node, column, columnI);
        layout.getChildren().add(node);
    }

}

package com.example.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MoveHistory {

    GridPane layout;
    int lastWhiteLabelI;
    int lastBlackLabelI;

    public MoveHistory(GridPane root, int width) {
        // the layout where the moves will be displayed
        layout = new GridPane();
        // scroll pane in case we overflow from the stage
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(Table.MOVES_HISTORY_WIDTH);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // column constraints of layout
        ColumnConstraints moveHistoryCollConstraints = new ColumnConstraints();
        moveHistoryCollConstraints.setPercentWidth(width);
        layout.getColumnConstraints().add(moveHistoryCollConstraints);

        GridPane generalLayout = new GridPane();
        GridPane columnTitles = new GridPane();

        // set the first column
        Label movesWhiteLabel = new Label();
        movesWhiteLabel.setText("White");
        movesWhiteLabel.setPrefWidth(100);
        GridPane.setConstraints(movesWhiteLabel, 0, 0);
        columnTitles.getChildren().add(movesWhiteLabel);
        // set the second column
        Label movesBlackLabel = new Label();
        movesBlackLabel.setText("Black");
        movesBlackLabel.setPrefWidth(100);
        GridPane.setConstraints(movesBlackLabel, 1, 0);
        columnTitles.getChildren().add(movesBlackLabel);

        GridPane.setConstraints(columnTitles, 0, 0);
        generalLayout.getChildren().add(columnTitles);
        GridPane.setConstraints(scrollPane, 0, 1);
        generalLayout.getChildren().add(scrollPane);

        GridPane.setConstraints(generalLayout, 1, 0);
        root.getChildren().add(generalLayout);

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

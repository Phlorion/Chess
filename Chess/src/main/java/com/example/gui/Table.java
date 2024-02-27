package com.example.gui;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;
import com.example.chess.util.BoardUtil;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Table {

    final static int BOARD_WIDTH = 512;
    final static int BOARD_HEIGHT = 512;
    final static int MENU_BAR_HEIGHT = 24;
    final static int MOVES_HISTORY_WIDTH = 160;
    final static int WIDTH = BOARD_WIDTH + MOVES_HISTORY_WIDTH;
    final static int HEIGHT = BOARD_HEIGHT + MENU_BAR_HEIGHT;
    final static int TILE_WIDTH = BOARD_WIDTH / Board.NUM_TILES_PER_ROW;
    final static int TILE_HEIGHT = BOARD_HEIGHT / Board.NUM_TILES_PER_COL;
    Stage primaryStage;
    GridPane boardGridPane;
    MoveHistory moveHistory;
    Board board;
    Map<Piece, ImageView> pieceMap;
    static Piece currentPieceHolder = null;

    public Table(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Chess"); // set title of the window
        primaryStage.setResizable(false); // set resizable to false
        GridPane windowLayout = new GridPane(); // create the base GridPane where we will place the menu bar and the game board
        Scene scene = new Scene(windowLayout, WIDTH, HEIGHT); // create a new Scene
        createMenuBar(windowLayout); // create the menu bar
        GridPane gameLayout = new GridPane();
        GridPane.setConstraints(gameLayout, 0, 1);
        windowLayout.getChildren().add(gameLayout);
        boardGridPane = createBoard(gameLayout); // create the game board
        moveHistory = new MoveHistory(gameLayout, MOVES_HISTORY_WIDTH); // create the moves history catalogue
        stage.setScene(scene);
        stage.show();
    }

    private void createMenuBar(GridPane layout) {
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefHeight(MENU_BAR_HEIGHT);
        menuBar.getMenus().addAll(menu1, menu2, menu3);

        GridPane.setConstraints(menuBar, 0, 0);
        layout.getChildren().add(menuBar);
        ColumnConstraints base = new ColumnConstraints();
        base.setPercentWidth(100);
        layout.getColumnConstraints().add(base);
    }

    private GridPane createBoard(GridPane layout) {
        // create the board GridPane
        GridPane gridBoard = new GridPane();
        for (int i=0; i < Board.NUM_TILES; i++) {
            Pane tile = new Pane();
            tile.setPrefSize(TILE_WIDTH, TILE_HEIGHT);
            if (((i / Board.NUM_TILES_PER_ROW) % 2 == 0 && (i % Board.NUM_TILES_PER_ROW) % 2 == 0)
            || ((i / Board.NUM_TILES_PER_ROW) % 2 != 0 && (i % Board.NUM_TILES_PER_ROW) % 2 != 0))
                tile.setStyle("-fx-background-color: #eeeed2;");
            else
                tile.setStyle("-fx-background-color: #769655;");
            GridPane.setConstraints(tile, i / Board.NUM_TILES_PER_ROW, i % Board.NUM_TILES_PER_ROW);
            gridBoard.getChildren().add(tile);
        }

        // add the indexes on the board
        for (int i=0; i<Board.NUM_TILES_PER_COL; i++) {
            Pane p = Table.getTileFromGridPane(gridBoard, i, 0);
            Label l_i = new Label();
            l_i.setText("" + (Board.NUM_TILES_PER_COL - i));
            l_i.setPrefWidth(TILE_WIDTH);
            l_i.setPrefHeight(TILE_HEIGHT);
            l_i.setFont(Font.font("Arial", 16));
            l_i.setAlignment(Pos.TOP_LEFT);
            if (i % 2 != 0) l_i.setTextFill(Color.web("#eeeed2"));
            else l_i.setTextFill(Color.web("#769655"));
            p.getChildren().add(l_i);
        }
        for (int j=0; j<Board.NUM_TILES_PER_ROW; j++) {
            Pane p = Table.getTileFromGridPane(gridBoard, Board.NUM_TILES_PER_COL-1, j);
            Label l_j = new Label();
            l_j.setText("" + BoardUtil.boardNumToString(j));
            l_j.setPrefWidth(TILE_WIDTH - 4);
            l_j.setPrefHeight(TILE_HEIGHT);
            l_j.setFont(Font.font("Arial", 16));
            l_j.setAlignment(Pos.BOTTOM_RIGHT);
            if (j % 2 != 0) l_j.setTextFill(Color.web("#769655"));
            else l_j.setTextFill(Color.web("#eeeed2"));
            p.getChildren().add(l_j);
        }

        GridPane.setConstraints(gridBoard, 0, 0);
        layout.getChildren().add(gridBoard);

        return gridBoard;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Set the pieces on the gui board based on the real board that is passed in this object
     * @throws FileNotFoundException If the image of a piece is not found then throw the exception
     */
    public void setPieces() throws FileNotFoundException {
        if (board == null) return; // if the board passed is null return
        flashTable(); // first clear all the pieces before drawing them again
        pieceMap = new HashMap<>();
        for (Tile tile : board.getAllTiles()) {
            Piece piece = tile.getPiece();
            if (piece != null) {
                ImageView pieceImage = new ImageView();
                if (piece.getType().equals(PiecesType.WHITE)) { // WHITE
                    switch (piece.getPieceKind()) {
                        case PAWN -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_plt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case ROOK -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_rlt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case KNIGHT -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_nlt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case BISHOP -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_blt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case QUEEN -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_qlt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case KING -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_klt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        default -> {
                        }
                    }
                } else { // BLACK
                    switch (piece.getPieceKind()) {
                        case PAWN -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_pdt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case ROOK -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_rdt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case KNIGHT -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_ndt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case BISHOP -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_bdt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case QUEEN -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_qdt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        case KING -> {
                            pieceImage.setImage(new Image(new FileInputStream("pieces/Chess_kdt60.png")));
                            pieceMap.put(piece, pieceImage);
                        }
                        default -> {
                        }
                    }
                }
                // set the image of the piece to the corresponding tile
                Pane gridPaneTile = Table.getTileFromGridPane(boardGridPane, piece.getPiecePosI(), piece.getPiecePosJ());
                pieceImage.setX(0); pieceImage.setY(0);
                pieceImage.setFitWidth(TILE_WIDTH);
                pieceImage.setFitHeight(TILE_HEIGHT);
                gridPaneTile.getChildren().add(pieceImage);

                // add event listeners to the tile
                addTileEventListeners(gridPaneTile);
            }
        }
    }

    private void addTileEventListeners(Pane tile) {
        tile.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (currentPieceHolder != null) return;
                int fromI = (int) (mouseEvent.getSceneY() - MENU_BAR_HEIGHT) / TILE_HEIGHT;
                int fromJ = (int) mouseEvent.getSceneX() / TILE_WIDTH;
                //System.out.println(fromI + ", " + fromJ);
                currentPieceHolder = board.getTile(fromI, fromJ).getPiece();
                // if we click on anything other than the current player's pieces, ignore
                if (currentPieceHolder != null && !currentPieceHolder.getType().equals(board.getCurrentPlayer().getType())) {
                    currentPieceHolder = null;
                } else if (currentPieceHolder != null) {
                    for (Move m : board.getCurrentPlayer().getAllLegalMoves()) {
                        if (m.getPiece().equals(currentPieceHolder)) {
                            Pane t = getTileFromGridPane(boardGridPane, m.getTo().getI(), m.getTo().getJ());
                            if (m.getClass().getName().equals("com.example.chess.move.CaptureMove")) {
                                Circle doughnut = new Circle();
                                doughnut.setCenterX(TILE_WIDTH / 2.);
                                doughnut.setCenterY(TILE_HEIGHT / 2.);
                                doughnut.setRadius(TILE_WIDTH / 2.5);
                                doughnut.setOpacity(0.4f);
                                t.getChildren().add(0, doughnut);
                                Circle inside = new Circle();
                                inside.setCenterX(TILE_WIDTH / 2.);
                                inside.setCenterY(TILE_HEIGHT / 2.);
                                inside.setRadius(TILE_WIDTH / 4.);
                                if ((m.getTo().getI() % 2 == 0 && m.getTo().getJ() % 2 != 0)
                                || (m.getTo().getI() % 2 != 0 && m.getTo().getJ() % 2 == 0)) inside.setStyle("-fx-fill: #769655;");
                                else inside.setStyle("-fx-fill: #eeeed2;");
                                t.getChildren().add(1, inside);
                            } else {
                                Circle circle = new Circle();
                                circle.setCenterX(TILE_WIDTH / 2.);
                                circle.setCenterY(TILE_HEIGHT / 2.);
                                circle.setRadius(TILE_WIDTH / 5.);
                                circle.setOpacity(0.4f);
                                t.getChildren().add(0, circle);
                            }
                        }
                    }
                }
                mouseEvent.consume();
            }
        });

        tile.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (currentPieceHolder != null) {
                    // when mouse released, first remove all the circles drawn to indicate the legal moves
                    for (int z=0; z<Board.NUM_TILES; z++) {
                        Pane t = getTileFromGridPane(boardGridPane, z / Board.NUM_TILES_PER_ROW, z % Board.NUM_TILES_PER_ROW);
                        List<Node> tempChildren = new ArrayList<>(t.getChildren());
                        for (Node child : tempChildren) {
                            if (!child.getClass().getName().equals("javafx.scene.image.ImageView")
                            && !child.getClass().getName().equals("javafx.scene.control.Label")) {
                                t.getChildren().remove(child);
                            }
                        }
                    }

                    int toI = (int) (mouseEvent.getSceneY() - MENU_BAR_HEIGHT) / TILE_HEIGHT;
                    int toJ = (int) mouseEvent.getSceneX() / TILE_WIDTH;
                    //System.out.println(toI + ", " + toJ);

                    Tile from = board.getTile(currentPieceHolder.getPiecePosI(), currentPieceHolder.getPiecePosJ());
                    Tile to = board.getTile(toI, toJ);
                    // check all the legal moves of the current player
                    for (Move m : board.getCurrentPlayer().getAllLegalMoves()) {
                        // if the move the current player is legal
                        if (m.getFrom().equals(from) && m.getTo().equals(to) && m.getPiece().equals(currentPieceHolder)) {
                            System.out.println(m);
                            // make the move and set the new board
                            setBoard(board.getCurrentPlayer().makeMove(m));
                            // set the pieces based on the new board
                            try {
                                setPieces();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            // add move to moves history
                            Label l = new Label();
                            l.setText(currentPieceHolder + ", " + BoardUtil.boardIndexToString(from.getI(), from.getJ()) + ", " + BoardUtil.boardIndexToString(to.getI(), to.getJ()));
                            if (currentPieceHolder.getType().equals(PiecesType.WHITE)) moveHistory.add(l, 0);
                            else moveHistory.add(l, 1);

                            System.out.println(board);
                            System.out.println(board.getCurrentPlayer() + "'s turn");

                            // check if check-mated or stale-mated
                            if (board.getCurrentPlayer().isCheckMated()) {
                                System.out.println(board.getOpponentPlayer() + " WINS!");
                                EndGame.CheckMate checkMate = new EndGame.CheckMate(board);
                                break;
                            } else if (board.getCurrentPlayer().isStaleMated()) {
                                System.out.println("TIE");
                                EndGame.StaleMate staleMate = new EndGame.StaleMate(board);
                                break;
                            }
                            break;
                        }
                    }
                }
                currentPieceHolder = null;
                mouseEvent.consume();
            }
        });
    }

    private void flashTable() {
        for (int z=0; z<Board.NUM_TILES; z++) {
            Pane tile = Table.getTileFromGridPane(boardGridPane, z / Board.NUM_TILES_PER_ROW, z % Board.NUM_TILES_PER_ROW);
            List<Node> tempTileChildren = new ArrayList<>(tile.getChildren());
            for (Node child : tempTileChildren) {
                if (!child.getClass().getName().equals("javafx.scene.control.Label"))
                    tile.getChildren().remove(child);
            }
        }
    }

    private static Pane getTileFromGridPane(GridPane gridPane, int i, int j) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == j && GridPane.getRowIndex(node) == i) {
                return (Pane) node;
            }
        }
        return null;
    }

    private static <Piece, ImageView> Piece getKeyByValue(Map<Piece, ImageView> map, ImageView value) {
        for (Map.Entry<Piece, ImageView> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}

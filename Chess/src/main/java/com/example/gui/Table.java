package com.example.gui;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Table {

    final static int WIDTH = 512;
    final static int HEIGHT = 536;
    final static int MENU_BAR_HEIGHT = 24;
    final static int TILE_WIDTH = WIDTH / Board.NUM_TILES_PER_ROW;
    final static int TILE_HEIGHT = (HEIGHT - MENU_BAR_HEIGHT) / Board.NUM_TILES_PER_COL;
    Stage primaryStage;
    GridPane boardGridPane;
    Board board;
    Map<Piece, ImageView> pieceMap;
    Piece currentPieceHolder = null;

    public Table(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Chess"); // set title of the window
        primaryStage.setResizable(false); // set resizable to false
        GridPane layout = new GridPane(); // create the base GridPane where we will place the menu bar and the game board
        Scene scene = new Scene(layout, WIDTH, HEIGHT); // create a new Scene
        createMenuBar(layout); // create the menu bar
        boardGridPane = createBoard(layout); // create the game board
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
        GridPane.setConstraints(gridBoard, 0, 1);
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
                            Pane tile = getTileFromGridPane(boardGridPane, m.getTo().getI(), m.getTo().getJ());
                            if (tile.getChildren().size() > 0) {
                                Circle doughnut = new Circle();
                                doughnut.setCenterX(TILE_WIDTH / 2.);
                                doughnut.setCenterY(TILE_HEIGHT / 2.);
                                doughnut.setRadius(TILE_WIDTH / 2.5);
                                doughnut.setOpacity(0.4f);
                                tile.getChildren().add(0, doughnut);
                                Circle inside = new Circle();
                                inside.setCenterX(TILE_WIDTH / 2.);
                                inside.setCenterY(TILE_HEIGHT / 2.);
                                inside.setRadius(TILE_WIDTH / 4.);
                                if ((m.getTo().getI() % 2 == 0 && m.getTo().getJ() % 2 != 0)
                                || (m.getTo().getI() % 2 != 0 && m.getTo().getJ() % 2 == 0)) inside.setStyle("-fx-fill: #769655;");
                                else inside.setStyle("-fx-fill: #eeeed2;");
                                tile.getChildren().add(1, inside);
                            } else {
                                Circle circle = new Circle();
                                circle.setCenterX(TILE_WIDTH / 2.);
                                circle.setCenterY(TILE_HEIGHT / 2.);
                                circle.setRadius(TILE_WIDTH / 5.);
                                circle.setOpacity(0.4f);
                                tile.getChildren().add(0, circle);
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
                            if (!child.getClass().getName().equals("javafx.scene.image.ImageView")) {
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
                            // make the move and set the new board
                            setBoard(board.getCurrentPlayer().makeMove(m));
                            // set the pieces based on the new board
                            try {
                                setPieces();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(board);
                            System.out.println(board.getCurrentPlayer() + "'s turn");
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
            Pane tile = getTileFromGridPane(boardGridPane, z / Board.NUM_TILES_PER_ROW, z % Board.NUM_TILES_PER_ROW);
            if (tile.getChildren().size() > 0)
                tile.getChildren().remove(0);
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

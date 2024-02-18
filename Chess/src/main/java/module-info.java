module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.chess.piece to javafx.fxml;
    exports com.example.chess.piece;
    exports com.example.chess.board;
    opens com.example.chess.board to javafx.fxml;
    exports com.example.chess.move;
    opens com.example.chess.move to javafx.fxml;
}
module launcher {
    requires tetris;
    requires samegame;
    requires tilegames;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports com.tmge.launcher;
    exports com.tmge.ui to javafx.fxml;
    opens com.tmge.ui to javafx.fxml;
}
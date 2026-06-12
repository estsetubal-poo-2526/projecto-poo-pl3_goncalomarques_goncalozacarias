package View;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application {

    public static final double LARGURA_JANELA = 800;
    public static final double ALTURA_JANELA = 900;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Wars");
        stage.setResizable(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);

        GestorCenas gestorCenas = new GestorCenas(stage);
        gestorCenas.mostrarMenu();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

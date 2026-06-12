package View;

import View.GestorCenas;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuView {

    private final GestorCenas gestorCenas;

    public MenuView(GestorCenas gestorCenas) {
        this.gestorCenas = gestorCenas;
    }

    public Scene criarCena() {
        StackPane raiz = new StackPane();
        raiz.setStyle("-fx-background-color: black;");

        // --- Título ---
        Text titulo = new Text("SPACE WARS");
        titulo.setFont(Font.font("Courier New", FontWeight.BOLD, 64));
        titulo.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.CYAN),
                new Stop(0.5, Color.WHITE),
                new Stop(1, Color.DEEPSKYBLUE)));
        titulo.setEffect(new Glow(0.8));

        Text subtitulo = new Text("► PREPARE FOR BATTLE ◄");
        subtitulo.setFont(Font.font("Courier New", FontWeight.NORMAL, 16));
        subtitulo.setFill(Color.web("#00ffcc", 0.8));

        // --- Botões ---
        Button btnIniciar = criarBotao("▶  INICIAR");
        Button btnFacil   = criarBotao("FÁCIL");
        Button btnDificil = criarBotao("DIFÍCIL");
        Button btnSair    = criarBotao("✕  SAIR");

        btnFacil.setVisible(false);
        btnFacil.setManaged(false);
        btnFacil.setDisable(true);
        btnDificil.setVisible(false);
        btnDificil.setManaged(false);
        btnDificil.setDisable(true);

        btnIniciar.setOnAction(e -> {
            btnFacil.setVisible(true);
            btnFacil.setManaged(true);
            btnFacil.setDisable(false);
            btnDificil.setVisible(true);
            btnDificil.setManaged(true);
            btnDificil.setDisable(false);
            btnIniciar.setDisable(true);
        });
        btnFacil.setOnAction(e -> {
            gestorCenas.setDificuldade("FACIL");
            gestorCenas.iniciarJogo();
        });
        btnDificil.setOnAction(e -> {
            gestorCenas.setDificuldade("DIFICIL");
            gestorCenas.iniciarJogo();
        });
        btnSair.setOnAction(e -> gestorCenas.getStage().close());

        VBox conteudo = new VBox(20, titulo, subtitulo, btnIniciar, btnFacil, btnDificil, btnSair);
        conteudo.setAlignment(Pos.CENTER);

        raiz.getChildren().addAll(conteudo);

        Scene cena = new Scene(raiz, App.LARGURA_JANELA, App.ALTURA_JANELA);
        cena.setFill(Color.BLACK);
        return cena;
    }

    // -------------------------------------------------------------------------
    // Helpers visuais
    // -------------------------------------------------------------------------

    private Button criarBotao(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(240);
        btn.setPrefHeight(48);
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #00ffcc;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #00ffcc;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 16;" +
                        "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #00ffcc22;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 16;" +
                        "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #00ffcc;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #00ffcc;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 16;" +
                        "-fx-cursor: hand;"
        ));
        return btn;
    }
}

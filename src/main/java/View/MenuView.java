package View;

import View.GestorCenas;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuView {

    private final GestorCenas gestorCenas;
    private final List<double[]> estrelas = new ArrayList<>();
    private AnimationTimer animacaoFundo;

    public MenuView(GestorCenas gestorCenas) {
        this.gestorCenas = gestorCenas;
        gerarEstrelas();
    }

    public Scene criarCena() {
        StackPane raiz = new StackPane();

        // --- Fundo animado com estrelas ---
        Canvas canvasFundo = new Canvas(App.LARGURA_JANELA, App.ALTURA_JANELA);
        GraphicsContext gc = canvasFundo.getGraphicsContext2D();

        animacaoFundo = new AnimationTimer() {
            @Override
            public void handle(long now) {
                desenharFundo(gc);
                moverEstrelas();
            }
        };
        animacaoFundo.start();

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
        Button btnJogar   = criarBotao("▶  JOGAR");
        Button btnSair    = criarBotao("✕  SAIR");

        btnJogar.setOnAction(e -> {
            animacaoFundo.stop();
            gestorCenas.iniciarJogo();
        });
        btnSair.setOnAction(e -> gestorCenas.getStage().close());

        VBox conteudo = new VBox(20, titulo, subtitulo, btnJogar, btnSair);
        conteudo.setAlignment(Pos.CENTER);

        raiz.getChildren().addAll(canvasFundo, conteudo);

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

    private void gerarEstrelas() {
        Random rnd = new Random();
        for (int i = 0; i < 120; i++) {
            // [x, y, tamanho, velocidade, brilho]
            estrelas.add(new double[]{
                    rnd.nextDouble() * App.LARGURA_JANELA,
                    rnd.nextDouble() * App.ALTURA_JANELA,
                    rnd.nextDouble() * 2.5 + 0.5,
                    rnd.nextDouble() * 1.5 + 0.3,
                    rnd.nextDouble()
            });
        }
    }

    private void desenharFundo(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, App.LARGURA_JANELA, App.ALTURA_JANELA);

        for (double[] e : estrelas) {
            double brilho = 0.4 + e[4] * 0.6;
            gc.setFill(Color.color(brilho, brilho, 1.0, brilho));
            gc.fillOval(e[0], e[1], e[2], e[2]);
        }
    }

    private void moverEstrelas() {
        for (double[] e : estrelas) {
            e[1] += e[3]; // movimento para baixo (parallax)
            if (e[1] > App.ALTURA_JANELA) {
                e[1] = 0;
                e[0] = Math.random() * App.LARGURA_JANELA;
            }
        }
    }
}
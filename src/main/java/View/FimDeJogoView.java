package View;

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

public class FimDeJogoView {

    private final GestorCenas gestorCenas;
    private final int pontuacao;

    public FimDeJogoView(GestorCenas gestorCenas, int pontuacao) {
        this.gestorCenas = gestorCenas;
        this.pontuacao = pontuacao;
    }

    public Scene criarCena() {
        StackPane raiz = new StackPane();
        raiz.setStyle("-fx-background-color: black;");

        Canvas canvas = new Canvas(App.LARGURA_JANELA, App.ALTURA_JANELA);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Título
        Text titulo = new Text("GAME OVER");
        titulo.setFont(Font.font("Courier New", FontWeight.BOLD, 72));
        titulo.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED),
                new Stop(0.5, Color.ORANGERED),
                new Stop(1, Color.YELLOW)));
        titulo.setEffect(new Glow(0.9));

        Text txtPontuacao = new Text("PONTUAÇÃO FINAL: " + pontuacao);
        txtPontuacao.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        txtPontuacao.setFill(Color.web("#00ffcc"));

        Button btnMenu   = criarBotao("◀  MENU PRINCIPAL");
        Button btnJogar  = criarBotao("↺  JOGAR DE NOVO");

        btnMenu.setOnAction(e -> {gestorCenas.mostrarMenu(); });
        btnJogar.setOnAction(e -> {gestorCenas.iniciarJogo(); });

        VBox conteudo = new VBox(22, titulo, txtPontuacao, btnJogar, btnMenu);
        conteudo.setAlignment(Pos.CENTER);

        raiz.getChildren().addAll(canvas, conteudo);

        Scene cena = new Scene(raiz, App.LARGURA_JANELA, App.ALTURA_JANELA);
        cena.setFill(Color.BLACK);
        return cena;
    }

    private Button criarBotao(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(48);
        String estiloBase =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #ff4444;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #ff4444;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 15;" +
                        "-fx-cursor: hand;";
        String estiloHover =
                "-fx-background-color: #ff444422;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 15;" +
                        "-fx-cursor: hand;";
        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e  -> btn.setStyle(estiloBase));
        return btn;
    }

    private void desenharFundo(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, App.LARGURA_JANELA, App.ALTURA_JANELA);
    }
}

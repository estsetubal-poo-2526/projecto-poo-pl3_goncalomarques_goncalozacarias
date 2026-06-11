package View;

import Jogo.Jogo;
import Jogo.Melhoria.Melhoria;
import Jogo.Melhoria.MelhoriaDano;
import Jogo.Melhoria.MelhoriaVelocidadeNave;
import Jogo.Melhoria.MelhoriaVelocidadeProjetil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class MelhoriasView {

    private final GestorCenas gestorCenas;
    private final Jogo jogo;

    // Melhorias disponíveis (instâncias placeholder — adaptar quando o modelo estiver completo)
    private final List<Melhoria> melhorias = List.of(
            new MelhoriaVelocidadeNave("Velocidade Nave", 0, 1),
            new MelhoriaDano("Dano", 0, 1),
            new MelhoriaVelocidadeProjetil("Velocidade Projétil", 0, 1)
    );

    private final String[] nomes    = { "Velocidade Nave", "Dano", "Velocidade Projétil" };
    private final String[] descricoes = {
            "Aumenta a velocidade de movimento da nave.",
            "Aumenta o dano causado pelos projéteis.",
            "Aumenta a velocidade dos projéteis disparados."
    };
    private final String[] icones   = { "🚀", "💥", "⚡" };

    public MelhoriasView(GestorCenas gestorCenas, Jogo jogo) {
        this.gestorCenas = gestorCenas;
        this.jogo = jogo;
    }

    public Scene criarCena() {
        VBox raiz = new VBox(30);
        raiz.setAlignment(Pos.CENTER);
        raiz.setPadding(new Insets(50));
        raiz.setStyle("-fx-background-color: #05050f;");

        // Título
        Text titulo = new Text("MELHORIAS");
        titulo.setFont(Font.font("Courier New", FontWeight.BOLD, 52));
        titulo.setFill(Color.web("#ffcc00"));
        titulo.setEffect(new Glow(0.7));

        Text subtitulo = new Text("Escolhe uma melhoria para a próxima onda");
        subtitulo.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
        subtitulo.setFill(Color.web("#aaaaaa"));

        // Painel de cards
        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER);

        for (int i = 0; i < melhorias.size(); i++) {
            cards.getChildren().add(criarCard(i));
        }

        // Botão continuar sem melhoria
        Button btnContinuar = criarBotaoSecundario("▶  CONTINUAR SEM MELHORIA");
        btnContinuar.setOnAction(e -> gestorCenas.iniciarJogo());

        raiz.getChildren().addAll(titulo, subtitulo, cards, btnContinuar);

        Scene cena = new Scene(raiz, App.LARGURA_JANELA, App.ALTURA_JANELA);
        return cena;
    }

    private VBox criarCard(int idx) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200, 240);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #0d0d2b;" +
                        "-fx-border-color: #ffcc00;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        );

        Text icone = new Text(icones[idx]);
        icone.setFont(Font.font(42));

        Text nome = new Text(nomes[idx]);
        nome.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        nome.setFill(Color.web("#ffcc00"));

        Text desc = new Text(descricoes[idx]);
        desc.setFont(Font.font("Courier New", 12));
        desc.setFill(Color.web("#cccccc"));
        desc.setWrappingWidth(170);

        Button btnEscolher = criarBotaoPrimario("ESCOLHER");
        Melhoria melhoria = melhorias.get(idx);
        btnEscolher.setOnAction(e -> {
            jogo.getJogador().adicionarMelhoria(melhoria);
            gestorCenas.iniciarJogo();
        });

        card.getChildren().addAll(icone, nome, desc, btnEscolher);

        // Hover
        card.setOnMouseEntered(ev -> card.setStyle(
                "-fx-background-color: #1a1a3a;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        ));
        card.setOnMouseExited(ev -> card.setStyle(
                "-fx-background-color: #0d0d2b;" +
                        "-fx-border-color: #ffcc00;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        ));

        return card;
    }

    private Button criarBotaoPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(140);
        btn.setPrefHeight(38);
        String base =
                "-fx-background-color: #ffcc00;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;";
        String hover =
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        return btn;
    }

    private Button criarBotaoSecundario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(280);
        btn.setPrefHeight(44);
        String base =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #555577;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #888899;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 14;" +
                        "-fx-cursor: hand;";
        String hover =
                "-fx-background-color: #ffffff11;" +
                        "-fx-border-color: #aaaacc;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-text-fill: #ccccdd;" +
                        "-fx-font-family: 'Courier New';" +
                        "-fx-font-size: 14;" +
                        "-fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        return btn;
    }
}
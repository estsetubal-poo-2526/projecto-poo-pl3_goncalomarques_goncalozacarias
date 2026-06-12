package View;
import Jogo.Jogo;
import Jogo.EstadoJogo;
import Jogo.Nave.Inimigo.Inimigo;
import Jogo.Nave.Inimigo.InimigoBoss;
import Jogo.Nave.Inimigo.InimigoForte;
import Jogo.Nave.Inimigo.InimigoRapido;
import Jogo.Nave.Jogador.NaveJogador;
import Jogo.Itens.Item;
import Jogo.Itens.ItemEscudo;
import Jogo.Itens.ItemVida;
import Jogo.Objetos.Projetil;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;

import java.util.*;

//Vista principal do jogo
public class JogoView {

    // ── Dimensões
    private static final double W = App.LARGURA_JANELA;
    private static final double H = App.ALTURA_JANELA;

    private static final double NAVE_W    = 60;
    private static final double NAVE_H    = 80;
    private static final double INIMIGO_W = 35;
    private static final double INIMIGO_H = 40;
    private static final double PROJ_W    = 4;
    private static final double PROJ_H    = 14;
    private static final double ITEM_W    = 35;
    private static final double ITEM_H    = 35;

    // ── Referências
    private final GestorCenas gestorCenas;
    private final Jogo jogo;

    // ── Canvas & loop
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private VBox painelPausa;
    private boolean pausado = false;

    // ── Input
    private final Set<KeyCode> teclasAtivas = new HashSet<>();
    private final Random rnd = new Random();

    // ── Tempo
    private long ultimoUpdate = 0;
    private static final double NS_POR_FRAME = 1_000_000_000.0 / 60.0; // 60 FPS

    private final Image naveImg =
            new Image(getClass().getResourceAsStream("/nave.png"));

    private final Image inimigoImg =
            new Image(getClass().getResourceAsStream("/naveInimiga.png"));

    private final Image inimigoRapidoImg =
            new Image(getClass().getResourceAsStream("/naveRapida.png"));

    private final Image inimigoForteImg =
            new Image(getClass().getResourceAsStream("/naveForte.png"));

    private final Image bossImg =
            new Image(getClass().getResourceAsStream("/boss.png"));

    private final Image vidaImg =
            new Image(getClass().getResourceAsStream("/itemVida.png"));

    private final Image escudoImg =
            new Image(getClass().getResourceAsStream("/itemEscudo.png"));

    public JogoView(GestorCenas gestorCenas, Jogo jogo) {
        this.gestorCenas = gestorCenas;
        this.jogo = jogo;
    }

    // Criação da cena
    public Scene criarCena() {
        canvas = new Canvas(W, H);
        gc = canvas.getGraphicsContext2D();

        painelPausa = criarPainelPausa();
        StackPane raiz = new StackPane(canvas, painelPausa);
        raiz.setStyle("-fx-background-color: black;");
        canvas.scaleXProperty().bind(Bindings.min(raiz.widthProperty().divide(W), raiz.heightProperty().divide(H)));
        canvas.scaleYProperty().bind(canvas.scaleXProperty());
        Scene cena = new Scene(raiz, W, H);
        cena.setFill(Color.BLACK);

        // Input
        cena.setOnKeyPressed(e  -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                alternarPausa();
                e.consume();
            } else if (!pausado) {
                teclasAtivas.add(e.getCode());
            }
        });
        cena.setOnKeyReleased(e -> teclasAtivas.remove(e.getCode()));
        cena.setOnMousePressed(e -> {
            if (!pausado) jogo.jogadorDisparar();
        });

        return cena;
    }

    private VBox criarPainelPausa() {
        Button btnContinuar = criarBotaoPausa("CONTINUAR");
        Button btnSair = criarBotaoPausa("SAIR");

        btnContinuar.setOnAction(e -> alternarPausa());
        btnSair.setOnAction(e -> {
            parar();
            MusicaBatalha.parar();
            gestorCenas.mostrarMenu();
        });

        javafx.scene.text.Text titulo = new javafx.scene.text.Text("PAUSA");
        titulo.setFont(Font.font("Courier New", FontWeight.BOLD, 52));
        titulo.setFill(Color.web("#00ffcc"));

        VBox painel = new VBox(20, titulo, btnContinuar, btnSair);
        painel.setAlignment(Pos.CENTER);
        painel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        painel.setStyle("-fx-background-color: #00000078;");
        painel.setPrefSize(W, H);
        painel.setVisible(false);
        return painel;
    }

    private Button criarBotaoPausa(String texto) {
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
        return btn;
    }

    private void alternarPausa() {
        pausado = !pausado;
        teclasAtivas.clear();
        painelPausa.setVisible(pausado);
        if(pausado) {
            MusicaBatalha.parar();
        }else{
            MusicaBatalha.iniciar();
        }
    }

    // Game Loop
    public void iniciar() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long agora) {
                if (ultimoUpdate == 0) { ultimoUpdate = agora; return; }

                double delta = (agora - ultimoUpdate) / NS_POR_FRAME;
                ultimoUpdate = agora;

                if (!pausado) {
                    processarInput();
                    jogo.atualizar();
                }
                renderizar();
                if (!pausado) verificarMudancaEstado();
            }
        };
        gameLoop.start();
    }

    public void parar() {
        if (gameLoop != null) gameLoop.stop();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Input → Modelo
    // ─────────────────────────────────────────────────────────────────────────

    private void processarInput() {
        NaveJogador jogador = jogo.getJogador();
        if (jogador == null) return;

        int dx = 0, dy = 0;
        if (teclasAtivas.contains(KeyCode.LEFT)  || teclasAtivas.contains(KeyCode.A)) dx = -1;
        if (teclasAtivas.contains(KeyCode.RIGHT) || teclasAtivas.contains(KeyCode.D)) dx =  1;
        if (teclasAtivas.contains(KeyCode.UP)    || teclasAtivas.contains(KeyCode.W)) dy = -1;
        if (teclasAtivas.contains(KeyCode.DOWN)  || teclasAtivas.contains(KeyCode.S)) dy =  1;

        if (dx != 0 || dy != 0) jogador.mover(dx, dy);
        if (teclasAtivas.contains(KeyCode.SPACE)) jogo.jogadorDisparar();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Verificar transições de estado
    // ─────────────────────────────────────────────────────────────────────────

    private void verificarMudancaEstado() {
        EstadoJogo estado = jogo.getState();
        if (estado == EstadoJogo.FIM_DE_JOGO) {
            parar();
            gestorCenas.mostrarFimDeJogo(jogo.getPontuacao());
        } else if (estado == EstadoJogo.MELHORIAS) {
            parar();
            gestorCenas.mostrarMelhorias(jogo);
        }
    }

    // Renderização
    private void renderizar() {
        // Fundo
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);

        // Objectos do jogo
        desenharInimigos();
        desenharProjeteis();
        desenharItens();
        desenharJogador();

        // HUD
        desenharHUD();
    }


    //Nave do Jogador
    private void desenharJogador() {

        NaveJogador jogador = jogo.getJogador();

        if (jogador == null) {
            return;
        }

        gc.drawImage(
                naveImg,
                jogador.getPosX(),
                jogador.getPosY(),
                NAVE_W,
                NAVE_H
        );

        if (jogador.isEscudoAtivo()) {
            gc.setStroke(Color.CYAN);
            gc.setLineWidth(3);

            gc.strokeOval(
                    jogador.getPosX() - 10,
                    jogador.getPosY() - 8,
                    NAVE_W + 20,
                    NAVE_H + 20
            );
        }
    }

    //Inimigos
    private void desenharInimigos() {
        List<Inimigo> inimigos = jogo.getInimigos();
        if (inimigos == null) return;
        for (Inimigo inimigo : inimigos) {
            desenharInimigo(inimigo);
        }
    }

    private void desenharInimigo(Inimigo inimigo) {

        double w = inimigo instanceof InimigoBoss ? 90 :
                inimigo instanceof InimigoForte ? 52 :
                        inimigo.getLargura();

        double h = inimigo instanceof InimigoBoss ? 90 :
                inimigo instanceof InimigoForte ? 52 :
                        inimigo.getAltura();

        Image sprite;

        if (inimigo instanceof InimigoBoss) {
            sprite = bossImg;
        } else if (inimigo instanceof InimigoForte) {
            sprite = inimigoForteImg;
        } else if (inimigo instanceof InimigoRapido) {
            sprite = inimigoRapidoImg;
        } else {
            sprite = inimigoImg;
        }

        gc.drawImage(
                sprite,
                inimigo.getPosX(),
                inimigo.getPosY(),
                w,
                h
        );

        int vidas = inimigo.getVidas();
        int maxVidas = inimigo.getMaxVidas();

        double pct = Math.max(
                0,
                Math.min(1.0, vidas / (double) maxVidas)
        );

        gc.setFill(Color.web("#333333"));
        gc.fillRect(
                inimigo.getPosX(),
                inimigo.getPosY() - 8,
                w,
                4
        );

        gc.setFill(
                pct > 0.5
                        ? Color.LIMEGREEN
                        : pct > 0.25
                        ? Color.ORANGE
                        : Color.RED
        );

        gc.fillRect(
                inimigo.getPosX(),
                inimigo.getPosY() - 8,
                w * pct,
                4
        );
    }

    private void desenharItens() {
        List<Item> itens = jogo.getItens();
        if (itens == null) return;
        for (Item item : itens) {
            desenharItem(item);
        }
    }

    private void desenharItem(Item item) {

        Image sprite =
                item instanceof ItemVida
                        ? vidaImg
                        : escudoImg;

        gc.drawImage(
                sprite,
                item.getPosX(),
                item.getPosY(),
                ITEM_W,
                ITEM_H
        );
    }

    // ── Projéteis ─────────────────────────────────────────────────────────────

    private void desenharProjeteis() {
        List<Projetil> projeteis = jogo.getProjeteis();
        if (projeteis == null) return;
        for (Projetil p : projeteis) {
            desenharProjetil(p);
        }
    }

    private void desenharProjetil(Projetil p) {
        double x = p.getPosX();
        double y = p.getPosY();

        // Brilho exterior
        gc.setFill(Color.web("#00ffff", 0.25));
        gc.fillOval(x - 3, y - 3, PROJ_W + 6, PROJ_H + 6);

        // Laser principal
        LinearGradient laser = new LinearGradient(0, y, 0, y + PROJ_H, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.CYAN));
        gc.setFill(laser);
        gc.fillRoundRect(x, y, PROJ_W, PROJ_H, 3, 3);
    }

    // ── HUD ───────────────────────────────────────────────────────────────────

    private void desenharHUD() {
        NaveJogador jogador = jogo.getJogador();
        int vidas      = jogador != null ? jogador.getVidas() : 0;
        int pontuacao  = jogo.getPontuacao();
        int moedas     = jogo.getMoedas();
        int onda        = jogo.getOnda();
        double nivel   = jogo.getNivelDificuldade();

        // Fundo HUD superior
        gc.setFill(Color.color(0, 0, 0, 0.55));
        gc.fillRect(0, 0, W, 44);

        gc.setFont(Font.font("Courier New", FontWeight.BOLD, 15));

        // Pontuação
        gc.setFill(Color.web("#00ffcc"));
        gc.fillText("PONTOS: " + pontuacao, 14, 27);

        // Nível de dificuldade
        gc.setFill(Color.web("#ff9900"));
        String nivelStr = "ONDA: " + onda;
        gc.fillText(nivelStr, W/2 - 40, 27);

        gc.setFill(Color.web("#ffcc00"));
        gc.fillText("MOEDAS: " + moedas, W/2 + 90, 27);

        // Vidas (corações pixelados)
        gc.setFill(Color.web("#ff4466"));
        for (int i = 0; i < vidas; i++) {
            desenharCoracao(W - 30 - i * 28, 12);
        }
    }

    private void desenharCoracao(double x, double y) {
        double s = 10;
        double[] cx = { x, x+s*0.5, x+s, x+s*1.2, x+s*1.4, x+s*1.4, x+s, x+s*0.5, x, x-s*0.4, x-s*0.4 };
        double[] cy = { y+s*0.5, y, y+s*0.5, y, y+s*0.4, y+s*0.8, y+s*1.3, y+s*1.6, y+s*1.3, y+s*0.8, y+s*0.4 };
        gc.fillPolygon(cx, cy, cx.length);
    }
}

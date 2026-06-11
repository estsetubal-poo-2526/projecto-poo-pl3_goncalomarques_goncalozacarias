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

import java.util.*;

/**
 * Vista principal do jogo.
 * Responsabilidades:
 *  - Capturar input do teclado e passar ao modelo (Jogo)
 *  - Correr o game loop via AnimationTimer
 *  - Renderizar todos os objetos no Canvas
 */
public class JogoView {

    // ── Dimensões ────────────────────────────────────────────────────────────
    private static final double W = App.LARGURA_JANELA;
    private static final double H = App.ALTURA_JANELA;

    // ── Tamanhos visuais dos sprites ─────────────────────────────────────────
    private static final double NAVE_W    = 40;
    private static final double NAVE_H    = 50;
    private static final double INIMIGO_W = 36;
    private static final double INIMIGO_H = 36;
    private static final double PROJ_W    = 4;
    private static final double PROJ_H    = 14;
    private static final double ITEM_W    = 22;
    private static final double ITEM_H    = 22;

    // ── Referências ──────────────────────────────────────────────────────────
    private final GestorCenas gestorCenas;
    private final Jogo jogo;

    // ── Canvas & loop ────────────────────────────────────────────────────────
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private VBox painelPausa;
    private boolean pausado = false;

    // ── Input ────────────────────────────────────────────────────────────────
    private final Set<KeyCode> teclasAtivas = new HashSet<>();

    // ── Fundo estrelas ───────────────────────────────────────────────────────
    private final List<double[]> estrelas = new ArrayList<>();
    private final Random rnd = new Random();

    // ── Tempo ────────────────────────────────────────────────────────────────
    private long ultimoUpdate = 0;
    private static final double NS_POR_FRAME = 1_000_000_000.0 / 60.0; // 60 FPS

    public JogoView(GestorCenas gestorCenas, Jogo jogo) {
        this.gestorCenas = gestorCenas;
        this.jogo = jogo;
        gerarEstrelas();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Criação da cena
    // ─────────────────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────────────────
    // Game Loop
    // ─────────────────────────────────────────────────────────────────────────

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
                    moverEstrelas(delta);
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

    // ─────────────────────────────────────────────────────────────────────────
    // Renderização
    // ─────────────────────────────────────────────────────────────────────────

    private void renderizar() {
        // Fundo
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        desenharEstrelas();

        // Objectos do jogo
        desenharInimigos();
        desenharProjeteis();
        desenharItens();
        desenharJogador();

        // HUD
        desenharHUD();
    }

    // ── Estrelas ──────────────────────────────────────────────────────────────

    private void gerarEstrelas() {
        for (int i = 0; i < 130; i++) {
            estrelas.add(new double[]{
                    rnd.nextDouble() * W,
                    rnd.nextDouble() * H,
                    rnd.nextDouble() * 2.2 + 0.4,
                    rnd.nextDouble() * 1.8 + 0.3,
                    rnd.nextDouble()
            });
        }
    }

    private void moverEstrelas(double delta) {
        for (double[] e : estrelas) {
            e[1] += e[3] * delta;
            if (e[1] > H) { e[1] = 0; e[0] = rnd.nextDouble() * W; }
        }
    }

    private void desenharEstrelas() {
        for (double[] e : estrelas) {
            double b = 0.4 + e[4] * 0.6;
            gc.setFill(Color.color(b, b, 1.0, b));
            gc.fillOval(e[0], e[1], e[2], e[2]);
        }
    }

    // ── Nave do Jogador ───────────────────────────────────────────────────────

    private void desenharJogador() {
        NaveJogador jogador = jogo.getJogador();
        if (jogador == null) return;

        double x = jogador.getPosX();
        double y = jogador.getPosY();

        // Motor (chama)
        gc.setFill(Color.color(1, 0.5, 0, 0.7));
        double[] cxMotor = { x + NAVE_W/2 - 8, x + NAVE_W/2, x + NAVE_W/2 + 8 };
        double[] cyMotor = { y + NAVE_H, y + NAVE_H + 14 + rnd.nextDouble()*6, y + NAVE_H };
        gc.fillPolygon(cxMotor, cyMotor, 3);

        // Corpo principal
        gc.setFill(Color.web("#1a8cff"));
        double[] cx = { x + NAVE_W/2, x + NAVE_W, x + NAVE_W * 0.75, x + NAVE_W * 0.25, x };
        double[] cy = { y, y + NAVE_H * 0.55, y + NAVE_H, y + NAVE_H, y + NAVE_H * 0.55 };
        gc.fillPolygon(cx, cy, 5);

        // Cockpit
        gc.setFill(Color.web("#00eeff", 0.85));
        gc.fillOval(x + NAVE_W/2 - 9, y + 8, 18, 18);

        // Contorno
        gc.setStroke(Color.web("#00ccff"));
        gc.setLineWidth(1.2);
        gc.strokePolygon(cx, cy, 5);

        if (jogador.isEscudoAtivo()) {
            double pulso = 0.75 + rnd.nextDouble() * 0.25;
            gc.setStroke(Color.web("#00ffff", pulso));
            gc.setLineWidth(3);
            gc.strokeOval(x - 10, y - 8, NAVE_W + 20, NAVE_H + 20);
            gc.setStroke(Color.web("#ffffff", 0.35));
            gc.setLineWidth(1);
            gc.strokeOval(x - 15, y - 13, NAVE_W + 30, NAVE_H + 30);
        }
    }

    // ── Inimigos ──────────────────────────────────────────────────────────────

    private void desenharInimigos() {
        List<Inimigo> inimigos = jogo.getInimigos();
        if (inimigos == null) return;
        for (Inimigo inimigo : inimigos) {
            desenharInimigo(inimigo);
        }
    }

    private void desenharInimigo(Inimigo inimigo) {
        double x = inimigo.getPosX();
        double y = inimigo.getPosY();
        double w = inimigo instanceof InimigoBoss ? 90 : inimigo instanceof InimigoForte ? 52 : INIMIGO_W;
        double h = inimigo instanceof InimigoBoss ? 90 : inimigo instanceof InimigoForte ? 52 : INIMIGO_H;
        Color corCorpo = Color.web("#ff3333");
        Color corContorno = Color.web("#ff8800");

        if (inimigo instanceof InimigoBoss) {
            corCorpo = Color.web("#111111");
            corContorno = Color.web("#ff0044");
        } else if (inimigo instanceof InimigoRapido) {
            corCorpo = Color.web("#ffcc00");
            corContorno = Color.web("#ffffff");
        } else if (inimigo instanceof InimigoForte) {
            corCorpo = Color.web("#9933ff");
            corContorno = Color.web("#ff66ff");
        }

        // Corpo hexagonal (inimigo estilo alien)
        gc.setFill(corCorpo);
        double[] cx = { x+w/2, x+w, x+w, x+w/2, x, x };
        double[] cy = { y, y+h*0.25, y+h*0.75, y+h, y+h*0.75, y+h*0.25 };
        gc.fillPolygon(cx, cy, 6);

        // Olhos
        gc.setFill(Color.web("#ffff00"));
        gc.fillOval(x + w*0.25 - 4, y + h*0.35, 8, 8);
        gc.fillOval(x + w*0.75 - 4, y + h*0.35, 8, 8);

        // Contorno
        gc.setStroke(corContorno);
        gc.setLineWidth(1.0);
        gc.strokePolygon(cx, cy, 6);

        // Barra de vida
        int vidas = inimigo.getVidas();
        int maxVidas = inimigo.getMaxVidas();
        double pct = Math.max(0, Math.min(1.0, vidas / (double) maxVidas));
        gc.setFill(Color.web("#333333"));
        gc.fillRect(x, y - 8, w, 4);
        gc.setFill(pct > 0.5 ? Color.LIMEGREEN : pct > 0.25 ? Color.ORANGE : Color.RED);
        gc.fillRect(x, y - 8, w * pct, 4);
    }

    private void desenharItens() {
        List<Item> itens = jogo.getItens();
        if (itens == null) return;
        for (Item item : itens) {
            desenharItem(item);
        }
    }

    private void desenharItem(Item item) {
        double x = item.getPosX();
        double y = item.getPosY();

        if (item instanceof ItemVida) {
            gc.setFill(Color.web("#ff4466", 0.25));
            gc.fillOval(x - 4, y - 4, ITEM_W + 8, ITEM_H + 8);
            gc.setFill(Color.web("#ff4466"));
            desenharCoracao(x + 6, y + 3);
        } else if (item instanceof ItemEscudo) {
            gc.setFill(Color.web("#00ccff", 0.25));
            gc.fillOval(x - 4, y - 4, ITEM_W + 8, ITEM_H + 8);
            gc.setStroke(Color.web("#00ccff"));
            gc.setLineWidth(2);
            gc.strokeOval(x, y, ITEM_W, ITEM_H);
            gc.strokeLine(x + ITEM_W / 2, y + 5, x + ITEM_W / 2, y + ITEM_H - 5);
        }
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

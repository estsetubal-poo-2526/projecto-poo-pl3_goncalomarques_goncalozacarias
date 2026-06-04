package View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    private Scene unicaScene; // Apenas UMA scene para o jogo todo

    // Os nossos layouts (em vez de Scenes)
    private VBox layoutMenu, layoutDificuldade, layoutMelhorias;

    private final String FUNDO_PRETO = "-fx-background-color: black;";
    private final String TITULO_ESTILO = "-fx-font-family: 'Courier New'; -fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: #00FF00;";
    private final String BOTAO_ESTILO = "-fx-background-color: transparent; -fx-font-family: 'Courier New'; -fx-font-size: 30px; -fx-text-fill: #800080; -fx-cursor: hand;";

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // 1. Criar os layouts
        criarMenuPrincipal();
        criarTelaDificuldade();
        criarTelaMelhorias();

        // 2. Criar a cena única baseada no primeiro ecrã (Menu)
        unicaScene = new Scene(layoutMenu, 1280, 720);

        stage.setTitle("SPACEWAR");
        stage.setScene(unicaScene);

        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Ativa a tela cheia inicial
        stage.show();
    }

    private void criarMenuPrincipal() {
        Label titulo = new Label("SPACEWAR");
        titulo.setStyle(TITULO_ESTILO);
        titulo.setAlignment(Pos.CENTER);

        Button btnIniciar = new Button("INICIAR");
        Button btnMelhorias = new Button("MELHORIAS");
        Button btnSair = new Button("SAIR");

        btnIniciar.setStyle(BOTAO_ESTILO);
        btnMelhorias.setStyle(BOTAO_ESTILO);
        btnSair.setStyle(BOTAO_ESTILO);

        // O TRUQUE: Em vez de setScene(), usamos setRoot()
        btnIniciar.setOnAction(e -> unicaScene.setRoot(layoutDificuldade));
        btnMelhorias.setOnAction(e -> unicaScene.setRoot(layoutMelhorias));
        btnSair.setOnAction(e -> Platform.exit());

        layoutMenu = new VBox(25, titulo, btnIniciar, btnMelhorias, btnSair);
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.setStyle(FUNDO_PRETO);
    }

    private void criarTelaDificuldade() {
        Label subTitulo = new Label("ESCOLHA A DIFICULDADE");
        subTitulo.setStyle(TITULO_ESTILO.replace("60px", "40px"));

        Button btnFacil = new Button("FACIL");
        Button btnDificil = new Button("DIFICIL");
        Button btnVoltar = new Button("VOLTAR");

        btnFacil.setStyle(BOTAO_ESTILO);
        btnDificil.setStyle(BOTAO_ESTILO);
        btnVoltar.setStyle(BOTAO_ESTILO + "-fx-font-size: 20px;");

        // Volta para o layout do menu
        btnVoltar.setOnAction(e -> unicaScene.setRoot(layoutMenu));

        layoutDificuldade = new VBox(20, subTitulo, btnFacil, btnDificil, btnVoltar);
        layoutDificuldade.setAlignment(Pos.CENTER);
        layoutDificuldade.setStyle(FUNDO_PRETO);
    }

    private void criarTelaMelhorias() {
        Label subTitulo = new Label("MELHORIAS DA NAVE");
        subTitulo.setStyle(TITULO_ESTILO.replace("60px", "40px"));

        Button btnAtaque = new Button("UPGRADE ATAQUE");
        Button btnVida = new Button("UPGRADE VIDA");
        Button btnVoltar = new Button("VOLTAR");

        btnAtaque.setStyle(BOTAO_ESTILO);
        btnVida.setStyle(BOTAO_ESTILO);
        btnVoltar.setStyle(BOTAO_ESTILO + "-fx-font-size: 20px;");

        // Volta para o layout do menu
        btnVoltar.setOnAction(e -> unicaScene.setRoot(layoutMenu));

        layoutMelhorias = new VBox(20, subTitulo, btnAtaque, btnVida, btnVoltar);
        layoutMelhorias.setAlignment(Pos.CENTER);
        layoutMelhorias.setStyle(FUNDO_PRETO);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
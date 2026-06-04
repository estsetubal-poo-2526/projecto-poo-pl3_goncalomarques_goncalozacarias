package View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.*;


public class App extends Application {

    //Stage e scenes que serão reutilizadas
    private Stage primaryStage;
    private Scene menuScene, dificuldadeScene, melhoriasScene;

    private final String FUNDO_PRETO = "-fx-background-color: black";
    private final String TITULO_ESTILO = "-fx-font-family: 'Courier New'; -fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: #00FF00;"; // Verde
    private final String BOTAO_ESTILO = "-fx-background-color: transparent; -fx-font-family: 'Courier New'; -fx-font-size: 30px; -fx-text-fill: #800080; -fx-cursor: hand;"; // Roxo

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Criar as 3 telas
        criarMenuPrincipal();
        criarTelaDificuldade();
        criarTelaMelhorias();

        // Configurações da Janela Principal
        stage.setTitle("SPACEWARS");
        stage.setScene(menuScene); // Começa no Menu
        stage.show();
    }

    // --- TELA 1: MENU PRINCIPAL ---
    private void criarMenuPrincipal() {
        Label titulo = new Label("SPACEWARS");
        titulo.setStyle(TITULO_ESTILO);
        titulo.setAlignment(Pos.CENTER);

        Button btnIniciar = new Button("INICIAR");
        Button btnMelhorias = new Button("MELHORIAS");
        Button btnSair = new Button("SAIR");

        // Estilizar botões
        btnIniciar.setStyle(BOTAO_ESTILO);
        btnMelhorias.setStyle(BOTAO_ESTILO);
        btnSair.setStyle(BOTAO_ESTILO);

        // Ações
        btnIniciar.setOnAction(e -> primaryStage.setScene(dificuldadeScene));
        btnMelhorias.setOnAction(e -> primaryStage.setScene(melhoriasScene));
        btnSair.setOnAction(e -> {
            Platform.exit();
        });

        VBox layout = new VBox(25, titulo, btnIniciar, btnMelhorias, btnSair);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(FUNDO_PRETO);

        menuScene = new Scene(layout, 1280, 720);
    }

    // --- TELA 2: ESCOLHA DE DIFICULDADE ---
    private void criarTelaDificuldade() {
        Label subTitulo = new Label("ESCOLHA A DIFICULDADE");
        subTitulo.setStyle(TITULO_ESTILO.replace("60px", "40px"));

        Button btnFacil = new Button("FACIL");
        Button btnDificil = new Button("DIFICIL");
        Button btnVoltar = new Button("VOLTAR");

        btnFacil.setStyle(BOTAO_ESTILO);
        btnDificil.setStyle(BOTAO_ESTILO);
        btnVoltar.setStyle(BOTAO_ESTILO + "-fx-font-size: 20px;");

        btnVoltar.setOnAction(e -> primaryStage.setScene(menuScene));

        VBox layout = new VBox(20, subTitulo, btnFacil, btnDificil, btnVoltar);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(FUNDO_PRETO);

        dificuldadeScene = new Scene(layout, 1280, 720);
    }

    //Tela melhorias
    private void criarTelaMelhorias() {
        Label subTitulo = new Label("MELHORIAS DA NAVE");
        subTitulo.setStyle(TITULO_ESTILO.replace("60px", "40px"));

        Button btnAtaque = new Button("UPGRADE ATAQUE");
        Button btnVida = new Button("UPGRADE VIDA");
        Button btnVoltar = new Button("VOLTAR");

        btnAtaque.setStyle(BOTAO_ESTILO);
        btnVida.setStyle(BOTAO_ESTILO);
        btnVoltar.setStyle(BOTAO_ESTILO + "-fx-font-size: 20px;");

        btnVoltar.setOnAction(e -> primaryStage.setScene(menuScene));

        VBox layout = new VBox(20, subTitulo, btnAtaque, btnVida, btnVoltar);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(FUNDO_PRETO);

        melhoriasScene = new Scene(layout, 1280, 720);
    }
    public static void main(String[] args){
        launch(args);
    }
}
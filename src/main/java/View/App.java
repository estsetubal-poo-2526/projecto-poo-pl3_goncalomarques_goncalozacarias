package View;

import javafx.application.Application;
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

    public void start(Stage menu) {
        Button botaoIniciar = new Button("Começar Jogo");
        Button botaoMelhoria = new Button("Melhorias");
        Button botaoSair = new Button("Sair");
        botaoSair.setOnAction(event -> {
            menu.close();
        });
        Label labelInicial = new Label("SPACEWARS");
        HBox topo = new HBox();
        VBox vbox = new VBox(50);
        vbox.setAlignment(Pos.CENTER);
        labelInicial.setStyle("-fx-font-size: 20px");
        vbox.setStyle("-fx-font-size: 20px");
        vbox.getChildren().addAll(botaoIniciar, botaoMelhoria, botaoSair);
        labelInicial.autosize();
        topo.getChildren().add(labelInicial);
        topo.setAlignment(Pos.TOP_CENTER);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);
        borderPane.setTop(topo);
        Scene scene = new Scene(borderPane, 400, 300);
        menu.setTitle("SpaceWars");
        menu.setScene(scene);
        menu.show();
    }

    // --- TELA 1: MENU PRINCIPAL ---
    private void criarMenuPrincipal() {
        Label titulo = new Label("SPACEWAR\nfor PC");
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
        btnSair.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(25, titulo, btnIniciar, btnMelhorias, btnSair);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(FUNDO_PRETO);

        menuScene = new Scene(layout, 1280, 720);
    }
    public static void main(String[] args){
        launch(args);
    }
}
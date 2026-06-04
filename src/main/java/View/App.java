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

    private final String fundo_preto = "-fx-background-color: black";
    private final String fundo_azul = "-fx-background-color: black";
    private final String fundo_roxo = "-fx-background-color: black";
    private final String fundo_tete = "-fx-background-color: black";

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
    public static void main(String[] args){
        launch(args);
    }
}
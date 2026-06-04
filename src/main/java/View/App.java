package View;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.*;


public class App extends Application {
    public void start(Stage menu) {
        Button botaoIniciar = new Button("Iniciar");
        Button botaoMelhoria = new Button("Melhorias");
        Button botaoSair = new Button("Sair");
        Label labelInicial = new Label("Bem vindo");
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(botaoIniciar);
        borderPane.setRight(botaoMelhoria);
        borderPane.setRight(botaoSair);
        borderPane.getChildren().addAll(botaoIniciar, botaoMelhoria, botaoSair, labelInicial);
        Scene scene = new Scene(borderPane, 400, 300);
        menu.setTitle("SpaceWars");
        menu.setScene(scene);
        menu.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
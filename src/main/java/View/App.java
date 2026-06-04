package View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.*;


public class App extends Application {
    public void start(Stage menu) {
        Button botaoIniciar = new Button("Começar Jogo");
        Button botaoMelhoria = new Button("Melhorias");
        Button botaoSair = new Button("Sair");
        botaoSair.setOnAction(event -> {
            menu.close();
        });
        Label labelInicial = new Label("Bem vindo");
        HBox topo = new HBox();
        VBox direita = new VBox(50);
        direita.setAlignment(Pos.CENTER_RIGHT);
        direita.autosize();
        direita.getChildren().addAll(botaoIniciar, botaoMelhoria, botaoSair);
        labelInicial.autosize();
        topo.getChildren().add(labelInicial);
        topo.setAlignment(Pos.TOP_CENTER);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(direita);
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
package View;

import Jogo.Jogo;
import Jogo.Limite;
import Jogo.Nave.Jogador.NaveJogador;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorCenas {

    private final Stage stage;
    private String dificuldade = "FACIL";

    public GestorCenas(Stage stage) {
        this.stage = stage;
    }

    public void mostrarMenu() {
        MenuView menu = new MenuView(this);
        trocarCena(menu.criarCena());
    }

    public void iniciarJogo() {
        Limite limite = new Limite(0, 800, 0, 900);
        NaveJogador jogador = new NaveJogador(400, 800, 40, 50, 5, limite, 3, 1);
        Jogo jogo = new Jogo(jogador, dificuldade);
        continuarJogo(jogo);
    }

    public void continuarJogo(Jogo jogo) {
        MusicaBatalha.iniciar();
        JogoView jogoView = new JogoView(this, jogo);
        trocarCena(jogoView.criarCena());
        jogoView.iniciar();
    }

    public void mostrarMelhorias(Jogo jogo) {
        MusicaBatalha.parar();
        MelhoriasView melhorias = new MelhoriasView(this, jogo);
        trocarCena(melhorias.criarCena());
    }

    public void mostrarFimDeJogo(int pontuacao) {
        MusicaBatalha.parar();
        FimDeJogoView fim = new FimDeJogoView(this, pontuacao);
        trocarCena(fim.criarCena());
    }

    private void trocarCena(Scene cena) {
        stage.setScene(cena);
        Platform.runLater(() -> stage.setFullScreen(true));
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public Stage getStage() {
        return stage;
    }
}

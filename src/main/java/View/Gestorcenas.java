package View;

import Jogo.Jogo;
import javafx.stage.Stage;

/**
 * Gere a navegação entre os diferentes ecrãs do jogo.
 * Centraliza a troca de cenas para que cada View não precise
 * de conhecer as outras diretamente.
 */
public class GestorCenas {

    private final Stage stage;

    public GestorCenas(Stage stage) {
        this.stage = stage;
    }

    /** Mostra o ecrã de menu principal. */
    public void mostrarMenu() {
        MenuView menu = new MenuView(this);
        stage.setScene(menu.criarCena());
    }

    /** Inicia uma nova partida e mostra o ecrã de jogo. */
    public void iniciarJogo() {
        Jogo jogo = new Jogo();
        JogoView jogoView = new JogoView(this, jogo);
        stage.setScene(jogoView.criarCena());
        jogoView.iniciar();
    }

    /** Mostra o ecrã de melhorias (entre ondas). */
    public void mostrarMelhorias(Jogo jogo) {
        MelhoriasView melhorias = new MelhoriasView(this, jogo);
        stage.setScene(melhorias.criarCena());
    }

    /** Mostra o ecrã de fim de jogo com a pontuação final. */
    public void mostrarFimDeJogo(int pontuacao) {
        FimDeJogoView fim = new FimDeJogoView(this, pontuacao);
        stage.setScene(fim.criarCena());
    }

    public Stage getStage() {
        return stage;
    }
}